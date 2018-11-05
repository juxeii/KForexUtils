package com.jforex.kforexutils.client

import arrow.core.Failure
import arrow.core.Success
import arrow.core.Try
import com.dukascopy.api.system.IClient
import com.jforex.kforexutils.authentification.*
import com.jforex.kforexutils.misc.FieldProperty
import com.jforex.kforexutils.settings.PlatformSettings
import com.jforex.kforexutils.system.ConnectionState
import io.reactivex.Observable

internal var IClient.connectionState: Observable<ConnectionState> by FieldProperty()
internal var IClient.pinProvider: PinProvider by FieldProperty()
internal var IClient.platformSettings: PlatformSettings by FieldProperty()

internal fun IClient.loginWithData(loginData: LoginData): Try<Unit> =
    with(loginData) {
        val username = credentials.username
        val password = credentials.password
        Try.invoke {
            maybePin.fold(
                { connect(jnlpAddress, username, password) },
                { pin -> connect(jnlpAddress, username, password, pin) })
        }
    }

internal fun IClient.waitForNextConnectionUpdate(): Try<ConnectionState> =
    Try.invoke {
        connectionState
            .take(1)
            .blockingFirst()
    }

internal fun IClient.filterConnectedState(state: ConnectionState): Try<ConnectionState> =
    waitForNextConnectionUpdate().flatMap {
        if (it == state) Success(it)
        else Failure(Exception("Connection state is not $state!"))
    }

fun IClient.login(credentials: LoginCredentials, type: LoginType = LoginType.DEMO): Try<ConnectionState> {
    val loginData = createLoginData(
        credentials = credentials,
        type = type,
        platformSettings = platformSettings,
        pinProvider = pinProvider
    )
    return loginWithData(loginData).flatMap { filterConnectedState(ConnectionState.CONNECTED) }
}

fun IClient.logout(): Try<ConnectionState> =
    Try
        .invoke { disconnect() }
        .flatMap { filterConnectedState(ConnectionState.DISCONNECTED) }