package com.jforex.kforexutils.client

import arrow.Kind
import arrow.effects.typeclasses.MonadDefer
import com.dukascopy.api.system.IClient
import com.jforex.kforexutils.authentification.LoginCredentials
import com.jforex.kforexutils.authentification.LoginType
import com.jforex.kforexutils.authentification.PinProvider
import com.jforex.kforexutils.client.LoginApi.login
import com.jforex.kforexutils.client.LoginApi.waitForConnect
import com.jforex.kforexutils.settings.PlatformSettings
import com.jforex.kforexutils.system.ConnectionState
import io.reactivex.Observable
import org.apache.logging.log4j.LogManager

private val logger = LogManager.getLogger()

interface LoginDependencies<F> : MonadDefer<F> {
    val client: IClient
    val platformSettings: PlatformSettings
    val pinProvider: PinProvider
    val connectionState: Observable<ConnectionState>

    companion object {
        operator fun <F> invoke(
            MD: MonadDefer<F>,
            client: IClient,
            platformSettings: PlatformSettings,
            pinProvider: PinProvider,
            connectionState: Observable<ConnectionState>
        ): LoginDependencies<F> =
            object : LoginDependencies<F>, MonadDefer<F> by MD {
                override val client = client
                override val platformSettings = platformSettings
                override val pinProvider = pinProvider
                override val connectionState = connectionState
            }
    }
}

fun <F> IClient.login(
    credentials: LoginCredentials,
    type: LoginType,
    usePin: Boolean,
    MD: MonadDefer<F>
): Kind<F, Unit> {
    val deps = LoginDependencies(
        MD,
        this,
        platformSettings,
        pinProvider,
        connectionState
    )

    return with(deps) {
        defer {
            login(credentials, type, usePin)
            waitForConnect()
        }
    }
}