package com.jforex.kforexutils.order.params.actions

import com.jforex.kforexutils.misc.*
import com.jforex.kforexutils.order.task.TaskRetry

data class OrderTaskActions(
    val onStart: KRunnable = emptyAction,
    val onSuccess: OrderConsumer = emptyOrderConsumer,
    val onError: ErrorConsumer = emptyErrorConsumer,
    val taskRetry: TaskRetry? = null
)