package com.jforex.kforexutils.order.task

import com.jforex.kforexutils.misc.emptyCallActions
import com.jforex.kforexutils.misc.emptyTaskRetryHandler

data class OrderCallParams(
    val callActions: OrderCallActions = emptyCallActions,
    val retryHandler: TaskRetry = emptyTaskRetryHandler
)