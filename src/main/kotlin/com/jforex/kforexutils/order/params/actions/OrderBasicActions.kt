package com.jforex.kforexutils.order.params.actions

import com.jforex.kforexutils.misc.ErrorConsumer
import com.jforex.kforexutils.misc.KRunnable
import com.jforex.kforexutils.misc.emptyAction
import com.jforex.kforexutils.misc.emptyErrorConsumer
import com.jforex.kforexutils.order.task.TaskRetry

data class OrderBasicActions(
    val onStart: KRunnable = emptyAction,
    val onError: ErrorConsumer = emptyErrorConsumer,
    val taskRetry: TaskRetry? = null
)