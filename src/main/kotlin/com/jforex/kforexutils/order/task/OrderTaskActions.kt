package com.jforex.kforexutils.order.task

import com.jforex.kforexutils.misc.*

data class OrderTaskActions(
    val onStart: KRunnable = emptyAction,
    val onSuccess: OrderConsumer = emptyOrderConsumer,
    val onError: ErrorConsumer = emptyErrorConsumer,
    val taskRetry: TaskRetry? = null
)