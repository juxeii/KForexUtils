package com.jforex.kforexutils.client

import com.dukascopy.api.system.IClient
import com.jforex.kforexutils.authentification.LoginCredentials
import com.jforex.kforexutils.authentification.LoginData
import com.jforex.kforexutils.authentification.LoginDataFactory
import com.jforex.kforexutils.authentification.LoginType
import com.jforex.kforexutils.misc.FieldProperty
import com.jforex.kforexutils.misc.KRunnable
import com.jforex.kforexutils.system.ConnectionState
import io.reactivex.Completable
import io.reactivex.Observable

internal var IClient.connectionState: Observable<ConnectionState> by FieldProperty()
internal var IClient.loginDataFactory: LoginDataFactory by FieldProperty()

internal fun IClient.loginWithData(loginData: LoginData) =
    with(loginData) {
        val username = credentials.username
        val password = credentials.password
        maybePin.fold(
            { connect(jnlpAddress, username, password) },
            { pin -> connect(jnlpAddress, username, password, pin) }
        )
    }

internal fun IClient.waitForNextConnectionUpdate() =
    connectionState
        .take(1)
        .ignoreElements()

internal fun IClient.actionWithConnectionUpdate(action: KRunnable) =
    Completable
        .fromAction { action() }
        .andThen { waitForNextConnectionUpdate() }

fun IClient.login(credentials: LoginCredentials, type: LoginType = LoginType.DEMO) {
    val loginData = loginDataFactory.create(credentials, type)
    actionWithConnectionUpdate { loginWithData(loginData) }
}

fun IClient.logout() = actionWithConnectionUpdate { disconnect() }