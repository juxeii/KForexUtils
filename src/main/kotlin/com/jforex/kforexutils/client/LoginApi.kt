package com.jforex.kforexutils.client

import arrow.Kind
import com.dukascopy.api.JFException
import com.jforex.kforexutils.authentification.LoginCredentials
import com.jforex.kforexutils.authentification.LoginType
import com.jforex.kforexutils.system.ConnectionState
import org.apache.logging.log4j.LogManager

private val logger = LogManager.getLogger()

internal object LoginApi
{
    fun <F> LoginDependencies<F>.login(
        credentials: LoginCredentials,
        type: LoginType,
        usePin: Boolean
    ): Kind<F, Unit> = catch {
        val username = credentials.username
        val password = credentials.password
        val connectUrl = if (type == LoginType.DEMO) platformSettings.demoConnectURL()
        else platformSettings.liveConnectURL()

        with(client) {
            if (usePin) connect(connectUrl, username, password, pinProvider.pin)
            else connect(connectUrl, username, password)
        }
    }

    fun <F> LoginDependencies<F>.waitForConnect(): Kind<F, Unit> =
        catch {
            connectionState
                .take(1)
                .map { state ->
                    if (state == ConnectionState.CONNECTED) Unit
                    else throw JFException("Wrong connection state $state after login!")
                }
                .blockingFirst()
        }
}