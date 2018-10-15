package com.jforex.kforexutils.order.event.consumer.data

import com.jforex.kforexutils.misc.OrderEventConsumer
import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.event.consumer.OrderEventHandlerType
import com.jforex.kforexutils.order.params.actions.OrderBasicActions

interface OrderEventConsumerData
{
    val eventHandlers: Map<OrderEventType, OrderEventConsumer>

    val finishEventTypes: Set<OrderEventType>

    val basicActions: OrderBasicActions

    val type: OrderEventHandlerType
}