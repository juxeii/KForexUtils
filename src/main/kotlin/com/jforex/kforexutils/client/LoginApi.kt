package com.jforex.kforexutils.client

import arrow.Kind
import arrow.core.Option
import com.jforex.kforexutils.authentification.LoginCredentials

object LoginApi
{
    fun <F> IClientApi<F>.login(
        credentials: LoginCredentials,
        maybePin: Option<String>
    ): Kind<F, Unit>
    {
        val username = credentials.username
        val password = credentials.password

        return defer {
            catch {
                with(client) {
                    maybePin.fold({ connect(platformSettings.demoConnectURL(), username, password) })
                    { pin -> connect(platformSettings.liveConnectURL(), username, password, pin) }
                }
            }
        }
    }
}