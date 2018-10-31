package com.jforex.kforexutils.authentification

import arrow.core.Option

data class LoginCredentials(
    val jnlpAddress: String,
    val username: String,
    val password: String,
    private val pin: String? = null
) {
    val maybePin: Option<String> = Option.fromNullable(pin)
}