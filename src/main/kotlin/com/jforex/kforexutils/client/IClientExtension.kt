package com.jforex.kforexutils.client

import com.dukascopy.api.system.IClient
import com.jforex.kforexutils.authentification.LoginCredentials
import com.jforex.kforexutils.misc.FieldProperty
import com.jforex.kforexutils.misc.KRunnable
import com.jforex.kforexutils.system.ConnectionState
import io.reactivex.Completable
import io.reactivex.Observable

internal var IClient.connectionState: Observable<ConnectionState> by FieldProperty()

internal fun IClient.loginWithCredentials(credentials: LoginCredentials) =
    credentials
        .maybePin
        .fold({ connect(credentials.jnlpAddress, credentials.username, credentials.password) },
            { connect(credentials.jnlpAddress, credentials.username, credentials.password, it) })

internal fun IClient.waitForNextConnectionUpdate() =
    connectionState
        .take(1)
        .ignoreElements()

internal fun IClient.actionWithConnectionUpdate(action: KRunnable) =
    Completable
        .fromAction { action() }
        .andThen { waitForNextConnectionUpdate() }

fun IClient.login(credentials: LoginCredentials) = actionWithConnectionUpdate { loginWithCredentials(credentials) }

fun IClient.logout() = actionWithConnectionUpdate { disconnect() }