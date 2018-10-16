package com.jforex.kforexutils.order.event.handler.data

import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.event.handler.OrderEventHandlerType
import com.jforex.kforexutils.order.params.actions.OrderBasicActions
import com.jforex.kforexutils.order.params.actions.OrderSLActions

data class SetSLEventConsumerData(private val slActions: OrderSLActions) :
    OrderEventConsumerData
{
    override val eventHandlers = mapOf(
        OrderEventType.CHANGED_SL to slActions.onSLChange,
        OrderEventType.CHANGE_REJECTED to slActions.onReject
    )
    override val finishEventTypes = setOf(
        OrderEventType.CHANGED_SL,
        OrderEventType.CHANGE_REJECTED
    )
    override val basicActions: OrderBasicActions
        get() = slActions.basicActions
    override val type: OrderEventHandlerType
        get() = OrderEventHandlerType.CHANGE_SL
}