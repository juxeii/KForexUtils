package com.jforex.kforexutils.order.task

import com.jforex.kforexutils.misc.emptyCallActions

data class OrderTaskParams(
    val eventParams: OrderTaskEventParams,
    val callActions: OrderCallActions = emptyCallActions
)