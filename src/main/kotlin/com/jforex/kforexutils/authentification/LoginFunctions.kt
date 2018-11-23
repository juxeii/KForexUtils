package com.jforex.kforexutils.authentification

import arrow.core.None
import arrow.core.Option
import arrow.data.ReaderApi
import arrow.data.map
import com.dukascopy.api.system.IClient
import com.jforex.kforexutils.client.pinProvider
import com.jforex.kforexutils.client.platformSettings

internal fun createLoginData(type: LoginType) = ReaderApi
    .ask<IClient>()
    .map {
        with(it.platformSettings) {
            if (type == LoginType.DEMO)
                LoginData(
                    jnlpAddress = demoConnectURL(),
                    maybePin = None
                )
            else LoginData(
                jnlpAddress = liveConnectURL(),
                maybePin = Option.just(it.pinProvider.pin)
            )
        }
    }