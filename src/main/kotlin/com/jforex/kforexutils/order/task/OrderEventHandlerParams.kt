package com.jforex.kforexutils.order.task

import com.jforex.kforexutils.misc.EventHandlers
import com.jforex.kforexutils.order.event.handler.data.OrderEventData

data class OrderEventHandlerParams(
    val eventData: OrderEventData,
    val eventHandlers: EventHandlers
)