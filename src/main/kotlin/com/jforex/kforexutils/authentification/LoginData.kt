package com.jforex.kforexutils.authentification

import arrow.core.Option

data class LoginData(
    val credentials: LoginCredentials,
    val jnlpAddress: String,
    val maybePin: Option<String> = Option.empty()
)