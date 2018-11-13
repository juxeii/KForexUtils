package com.jforex.kforexutils.order.task

import com.jforex.kforexutils.misc.KRunnable

data class OrderTaskExecutionParams(
    val eventParams: OrderTaskEventParams,
    val retryCall: KRunnable,
    val retryHandler: TaskRetry
)