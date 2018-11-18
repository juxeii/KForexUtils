package com.jforex.kforexutils.authentification

import arrow.core.Option
import com.jforex.kforexutils.settings.PlatformSettings

internal fun createLoginData(
    credentials: LoginCredentials,
    type: LoginType,
    pinProvider: PinProvider,
    platformSettings: PlatformSettings
) = LoginData(
    credentials = credentials,
    jnlpAddress = jNLPAddressForLoginType(type, platformSettings),
    maybePin = pinForLoginType(type, pinProvider)
)

internal fun jNLPAddressForLoginType(
    type: LoginType,
    platformSettings: PlatformSettings
) = if (type == LoginType.DEMO) platformSettings.demoConnectURL()
else platformSettings.liveConnectURL()


internal fun pinForLoginType(
    type: LoginType,
    pinProvider: PinProvider
) = if (type == LoginType.DEMO) Option.empty()
else Option.just(pinProvider.pin)
