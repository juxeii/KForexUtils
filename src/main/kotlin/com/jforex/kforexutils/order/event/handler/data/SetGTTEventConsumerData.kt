package com.jforex.kforexutils.order.event.handler.data

import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.event.handler.OrderEventHandlerType
import com.jforex.kforexutils.order.params.actions.OrderGTTActions

data class SetGTTEventConsumerData(private val actions: OrderGTTActions) :
    OrderEventConsumerData
{
    override val eventHandlers = mapOf(
        OrderEventType.CHANGED_GTT to actions.onGTTChange,
        OrderEventType.CHANGE_REJECTED to actions.onReject
    )
    override val finishEventTypes = setOf(
        OrderEventType.CHANGED_GTT,
        OrderEventType.CHANGE_REJECTED
    )
    override val basicActions = actions.basicActions
    override val type = OrderEventHandlerType.CHANGE_GTT
}