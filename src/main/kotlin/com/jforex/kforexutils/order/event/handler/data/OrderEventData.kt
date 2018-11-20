package com.jforex.kforexutils.order.event.handler.data

import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.event.handler.OrderEventHandlerType

interface OrderEventData
{
    val allEventTypes: Set<OrderEventType>
    val finishEventTypes: Set<OrderEventType>
    val handlerType: OrderEventHandlerType
}