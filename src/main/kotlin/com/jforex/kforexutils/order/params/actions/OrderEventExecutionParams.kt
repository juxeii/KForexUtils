package com.jforex.kforexutils.order.params.actions

import com.jforex.kforexutils.misc.KRunnable
import com.jforex.kforexutils.misc.emptyAction
import com.jforex.kforexutils.misc.emptyTaskRetryHandler
import com.jforex.kforexutils.order.event.OrderTaskEventParams
import com.jforex.kforexutils.order.task.TaskRetry

data class OrderEventExecutionParams(
    val eventParams: OrderTaskEventParams,
    val retryCall: KRunnable,
    val retryHandler: TaskRetry
)