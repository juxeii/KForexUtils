package com.jforex.kforexutils.order.task

import com.jforex.kforexutils.misc.EventHandlers
import com.jforex.kforexutils.misc.emptyOrderEventHandlers
import com.jforex.kforexutils.order.event.handler.data.OrderEventData

data class OrderTaskEventParams(
    val eventData: OrderEventData,
    val eventHandlers: EventHandlers = emptyOrderEventHandlers
)