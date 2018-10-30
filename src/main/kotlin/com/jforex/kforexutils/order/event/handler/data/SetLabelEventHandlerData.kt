package com.jforex.kforexutils.order.event.handler.data

import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.event.handler.OrderEventHandlerType
import com.jforex.kforexutils.order.params.actions.OrderLabelActions

data class SetLabelEventHandlerData(private val actions: OrderLabelActions) : OrderEventHandlerData
{
    override val eventHandlers = mapOf(
        OrderEventType.CHANGED_LABEL to actions.onLabelChange
    )
    override val finishEventTypes = setOf(
        OrderEventType.CHANGED_LABEL,
        OrderEventType.CHANGE_REJECTED
    )
    override val completeEventTypes = finishEventTypes.minus(OrderEventType.CHANGE_REJECTED)
    override val rejectEventType = OrderEventType.CHANGE_REJECTED
    override val taskActions = actions.taskActions
    override val type = OrderEventHandlerType.CHANGE
}