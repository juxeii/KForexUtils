package com.jforex.kforexutils.order.task

import com.jforex.kforexutils.misc.*

data class OrderCallActions(
    val onStart: KRunnable = emptyAction,
    val onSuccess: OrderConsumer = emptyOrderConsumer,
    val onError: ErrorConsumer = emptyErrorConsumer
)