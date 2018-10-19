package com.jforex.kforexutils.order.event.handler.data

import com.jforex.kforexutils.misc.OrderEventConsumer
import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.event.handler.OrderEventHandlerType
import com.jforex.kforexutils.order.params.OrderRetryParams
import com.jforex.kforexutils.order.params.actions.OrderBasicActions

interface OrderEventHandlerData
{
    val eventHandlers: Map<OrderEventType, OrderEventConsumer>

    val finishEventTypes: Set<OrderEventType>

    val rejectEventTypes: Set<OrderEventType>

    val basicActions: OrderBasicActions

    val type: OrderEventHandlerType

    val retryParams: OrderRetryParams
}