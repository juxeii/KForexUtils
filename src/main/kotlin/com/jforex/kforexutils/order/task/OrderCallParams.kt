package com.jforex.kforexutils.order.task

import com.jforex.kforexutils.misc.emptyTaskData
import com.jforex.kforexutils.misc.emptyTaskRetryHandler

data class OrderCallParams(
    val taskData: OrderTaskData = emptyTaskData,
    val retryHandler: TaskRetry = emptyTaskRetryHandler
)