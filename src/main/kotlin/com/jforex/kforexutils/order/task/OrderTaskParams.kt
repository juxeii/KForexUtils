package com.jforex.kforexutils.order.task

import com.jforex.kforexutils.misc.emptyCallHandlers

data class OrderTaskParams(
    val eventHandlerParams: OrderEventHandlerParams,
    val callHandlers: OrderCallHandlers = emptyCallHandlers
)