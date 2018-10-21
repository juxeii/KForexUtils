package com.jforex.kforexutils.order.event.handler.data

import com.jforex.kforexutils.misc.OrderEventConsumer
import com.jforex.kforexutils.misc.RejectOrderEventConsumer
import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.event.handler.OrderEventHandlerType
import com.jforex.kforexutils.order.params.actions.OrderBasicActions

interface OrderEventHandlerData
{
    val eventHandlers: Map<OrderEventType, OrderEventConsumer>

    val rejectEventHandler: RejectOrderEventConsumer

    val finishEventTypes: Set<OrderEventType>

    val rejectEventType: OrderEventType

    val basicActions: OrderBasicActions

    val type: OrderEventHandlerType
}