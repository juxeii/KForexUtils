package com.jforex.kforexutils.authentification

import arrow.core.None
import arrow.core.Some
import arrow.data.ReaderApi
import arrow.data.fix
import arrow.data.map
import arrow.instances.applicative
import com.dukascopy.api.system.IClient
import com.jforex.kforexutils.client.pinProvider
import com.jforex.kforexutils.client.platformSettings

internal fun createLoginData(type: LoginType) = ReaderApi
    .applicative<IClient>()
    .tupled(jNLPAddressForLoginType(type), pinForLoginType(type))
    .fix()
    .map { LoginData(jnlpAddress = it.a, maybePin = it.b) }

internal fun jNLPAddressForLoginType(type: LoginType) = ReaderApi
    .ask<IClient>()
    .map {
        with(it.platformSettings) {
            if (type == LoginType.DEMO) demoConnectURL()
            else liveConnectURL()
        }
    }

internal fun pinForLoginType(type: LoginType) = ReaderApi
    .ask<IClient>()
    .map {
        if (type == LoginType.DEMO) None
        else Some(it.pinProvider.pin)
    }