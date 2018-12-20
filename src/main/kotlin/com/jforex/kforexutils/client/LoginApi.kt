package com.jforex.kforexutils.client

import arrow.Kind
import arrow.typeclasses.bindingCatch
import com.dukascopy.api.JFException
import com.jforex.kforexutils.authentification.LoginCredentials
import com.jforex.kforexutils.authentification.LoginType
import com.jforex.kforexutils.system.ConnectionState

internal object LoginApi
{
    fun <F> LoginDependencies<F>.login(
        credentials: LoginCredentials,
        type: LoginType = LoginType.DEMO
    ): Kind<F, Unit> = catch {
        val username = credentials.username
        val password = credentials.password

        with(client) {
            if (type == LoginType.DEMO) connect(platformSettings.demoConnectURL(), username, password)
            else connect(platformSettings.liveConnectURL(), username, password, pinProvider.pin)
        }
    }

    fun <F> LoginDependencies<F>.waitForConnect(): Kind<F, Unit> =
        bindingCatch {
            connectionState
                .take(1)
                .map { state ->
                    if (state == ConnectionState.CONNECTED) Unit
                    else throw JFException("Wrong connection state $state after login!")
                }
                .blockingFirst()
        }
}