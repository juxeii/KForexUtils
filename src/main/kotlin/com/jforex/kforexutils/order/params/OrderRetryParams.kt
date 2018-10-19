package com.jforex.kforexutils.order.params

data class OrderRetryParams(
    val attempts: Int = 0,
    val delay: Long = 0L
)