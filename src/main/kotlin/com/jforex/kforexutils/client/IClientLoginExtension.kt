package com.jforex.kforexutils.client

import arrow.core.None
import arrow.core.Option
import arrow.core.some
import arrow.data.ReaderT
import arrow.data.fix
import arrow.effects.ForIO
import arrow.effects.IO
import arrow.effects.fix
import arrow.effects.instances.io.monad.monad
import arrow.instances.kleisli.monad.monad
import arrow.typeclasses.binding
import com.dukascopy.api.JFException
import com.dukascopy.api.system.IClient
import com.jforex.kforexutils.authentification.LoginCredentials
import com.jforex.kforexutils.authentification.LoginType
import com.jforex.kforexutils.authentification.PinProvider
import com.jforex.kforexutils.misc.FieldProperty
import com.jforex.kforexutils.settings.PlatformSettings
import com.jforex.kforexutils.system.ConnectionState
import com.jforex.kforexutils.system.KSystemListener
import io.reactivex.Observable

internal var IClient.systemListener: KSystemListener by FieldProperty()
internal var IClient.platformSettings: PlatformSettings by FieldProperty()
internal var IClient.pinProvider: PinProvider by FieldProperty()

fun IClient.login(
    credentials: LoginCredentials,
    type: LoginType = LoginType.DEMO
) = ReaderT.monad<ForIO, IClient>(IO.monad()).binding {
    val maybePin =
        if (type == LoginType.DEMO) None
        else getPinFromDialog
            .local<IClient> { pinProvider }
            .bind()
            .some()

    connect(credentials, maybePin).bind()
    waitForConnectState
        .local<IClient> { systemListener.connectionState }
        .bind()
}.fix().run(this).fix()

internal fun connect(
    credentials: LoginCredentials,
    maybePin: Option<String>
) = ReaderT { client: IClient ->
    IO {
        val username = credentials.username
        val password = credentials.password
        with(client) {
            maybePin.fold({ connect(platformSettings.demoConnectURL(), username, password) })
            { pin -> connect(platformSettings.liveConnectURL(), username, password, pin) }
        }
    }
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

internal val getPinFromDialog = ReaderT { pinProvider: PinProvider ->
    IO { pinProvider.pin }
}