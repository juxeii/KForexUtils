package com.jforex.kforexutils.order.task

import com.jforex.kforexutils.order.event.handler.data.OrderEventData

data class OrderTaskParams(
    val callHandlers: OrderCallHandlers,
    val eventData: OrderEventData
)