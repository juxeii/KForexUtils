package com.jforex.kforexutils.client

import arrow.core.None
import arrow.core.some
import arrow.data.ReaderT
import arrow.effects.IO
import arrow.effects.typeclasses.MonadDefer
import com.dukascopy.api.JFException
import com.dukascopy.api.system.IClient
import com.jforex.kforexutils.authentification.LoginCredentials
import com.jforex.kforexutils.authentification.LoginType
import com.jforex.kforexutils.authentification.PinProvider
import com.jforex.kforexutils.client.LoginApi.login
import com.jforex.kforexutils.misc.FieldProperty
import com.jforex.kforexutils.settings.PlatformSettings
import com.jforex.kforexutils.system.ConnectionState
import com.jforex.kforexutils.system.KSystemListener
import io.reactivex.Observable

internal var IClient.systemListener: KSystemListener by FieldProperty()
internal var IClient.platformSettings: PlatformSettings by FieldProperty()
internal var IClient.pinProvider: PinProvider by FieldProperty()

fun <F> IClient.login(
    credentials: LoginCredentials,
    type: LoginType = LoginType.DEMO,
    AE: MonadDefer<F>
)
{
    val clientApi = object : IClientApi<F>, MonadDefer<F> by AE
    {
        override val client = this@login
        override val platformSettings = client.platformSettings
    }

    val maybePin =
        if (type == LoginType.DEMO) None
        else pinProvider.pin.some()

    clientApi.login(credentials, maybePin)
}

internal val waitForConnectState = ReaderT { connectionState: Observable<ConnectionState> ->
    IO {

        connectionState
            .take(1)
            .map { state ->
                if (state == ConnectionState.CONNECTED) Unit
                else throw JFException("Wrong connection state $state after login!")
            }
            .blockingFirst()
    }
}

interface IClientApi<F> : MonadDefer<F>
{
    val client: IClient
    val platformSettings: PlatformSettings
}
