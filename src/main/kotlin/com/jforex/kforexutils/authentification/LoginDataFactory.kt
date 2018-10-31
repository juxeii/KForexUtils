package com.jforex.kforexutils.authentification

import arrow.core.Option
import com.jforex.kforexutils.misc.platformSettings

class LoginDataFactory(private val pinProvider: PinProvider) {

    fun create(
        credentials: LoginCredentials,
        type: LoginType = LoginType.DEMO
    ) = LoginData(
        credentials = credentials,
        jnlpAddress = chooseJNLPAddress(type),
        maybePin = getPin(type)
    )

    private fun chooseJNLPAddress(type: LoginType) =
        if (type == LoginType.DEMO) platformSettings.demoConnectURL()
        else platformSettings.liveConnectURL()

    private fun getPin(type: LoginType) =
        if (type == LoginType.DEMO) Option.empty()
        else Option.just(pinProvider.pin)
}