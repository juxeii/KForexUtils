package com.jforex.kforexutils.client

import arrow.data.ReaderApi
import arrow.data.flatMap
import arrow.data.map
import arrow.data.runId
import com.dukascopy.api.system.IClient
import com.jforex.kforexutils.authentification.*
import com.jforex.kforexutils.misc.FieldProperty
import com.jforex.kforexutils.settings.PlatformSettings
import com.jforex.kforexutils.system.ConnectionState
import com.jforex.kforexutils.system.KSystemListener
import io.reactivex.Completable

internal var IClient.systemListener: KSystemListener by FieldProperty()
internal var IClient.platformSettings: PlatformSettings by FieldProperty()
internal var IClient.pinProvider: PinProvider by FieldProperty()

fun IClient.login(
    credentials: LoginCredentials,
    type: LoginType = LoginType.DEMO
) = createLoginData(credentials, type)
    .flatMap { connect(it) }
    .flatMap { filterConnectionState(it, ConnectionState.CONNECTED) }
    .runId(this)

internal fun connect(loginData: LoginData) = ReaderApi
    .ask<IClient>()
    .map { client ->
        val username = loginData.credentials.username
        val password = loginData.credentials.password
        Completable.fromCallable {
            with(loginData) {
                maybePin.fold(
                    { client.connect(jnlpAddress, username, password) },
                    { pin -> client.connect(jnlpAddress, username, password, pin) })
            }
        }
    }

internal fun filterConnectionState(
    completable: Completable,
    state: ConnectionState
) = ReaderApi
    .ask<IClient>()
    .map { client ->
        val waitForState = client
            .systemListener
            .connectionState
            .take(1)
            .map {
                if (it == state) state
                else throw Exception("Connection state is not $state!")
            }
            .ignoreElements()
        completable.andThen(waitForState)
    }