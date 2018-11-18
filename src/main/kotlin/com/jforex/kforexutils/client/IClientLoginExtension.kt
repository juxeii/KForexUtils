package com.jforex.kforexutils.client

import com.dukascopy.api.system.IClient
import com.jforex.kforexutils.authentification.*
import com.jforex.kforexutils.misc.FieldProperty
import com.jforex.kforexutils.settings.PlatformSettings
import com.jforex.kforexutils.system.ConnectionState
import io.reactivex.Completable
import io.reactivex.Observable

internal var IClient.connectionState: Observable<ConnectionState> by FieldProperty()
internal var IClient.pinProvider: PinProvider by FieldProperty()
internal var IClient.platformSettings: PlatformSettings by FieldProperty()

fun IClient.login(
    credentials: LoginCredentials,
    type: LoginType = LoginType.DEMO
): Completable
{
    val loginData = createLoginData(
        credentials = credentials,
        type = type,
        platformSettings = platformSettings,
        pinProvider = pinProvider
    )
    return Completable
        .fromCallable(login(loginData))
        .andThen(filterConnectedState(ConnectionState.CONNECTED))
}

internal fun IClient.login(loginData: LoginData) = {
    with(loginData) {
        val username = credentials.username
        val password = credentials.password
        Completable.fromCallable {
            maybePin.fold(
                { connect(jnlpAddress, username, password) },
                { pin -> connect(jnlpAddress, username, password, pin) })
        }
    }
}

internal fun IClient.filterConnectedState(state: ConnectionState) =
    connectionState
        .take(1)
        .map {
            if (it == state) state
            else throw Exception("Connection state is not $state!")
        }
        .ignoreElements()

fun IClient.logout() = Completable
    .fromCallable { disconnect() }
    .andThen(filterConnectedState(ConnectionState.DISCONNECTED))