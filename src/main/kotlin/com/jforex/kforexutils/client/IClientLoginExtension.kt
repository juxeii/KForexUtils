package com.jforex.kforexutils.client

import com.dukascopy.api.system.IClient
import com.jforex.kforexutils.authentification.LoginCredentials
import com.jforex.kforexutils.authentification.LoginType
import com.jforex.kforexutils.authentification.PinProvider
import com.jforex.kforexutils.authentification.createLoginData
import com.jforex.kforexutils.misc.FieldProperty
import com.jforex.kforexutils.system.ConnectionState
import io.reactivex.Completable

internal var IClient.context: IClientContext by FieldProperty()

fun IClient.login(
    credentials: LoginCredentials,
    type: LoginType = LoginType.DEMO
): Completable
{
    val loginData = createLoginData(
        type = type,
        platformSettings = context.platformSettings,
        pinProvider = PinProvider(this, context.platformSettings.liveConnectURL())
    )
    val username = credentials.username
    val password = credentials.password
    with(loginData) {
        return Completable
            .fromCallable {
                maybePin.fold(
                    { connect(jnlpAddress, username, password) },
                    { pin -> connect(jnlpAddress, username, password, pin) })
            }.andThen(filterConnectionState(ConnectionState.CONNECTED))
    }
}

internal fun IClient.filterConnectionState(state: ConnectionState) =
    context
        .systemListener
        .connectionState
        .take(1)
        .map {
            if (it == state) state
            else throw Exception("Connection state is not $state!")
        }
        .ignoreElements()

fun IClient.logout() = Completable
    .fromCallable { disconnect() }
    .andThen(filterConnectionState(ConnectionState.DISCONNECTED))