package com.jforex.kforexutils.order.event.handler.data

import com.jforex.kforexutils.misc.KRunnable
import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.event.handler.OrderEventHandlerType
import com.jforex.kforexutils.order.params.actions.OrderSLActions

data class SetSLEventHandlerData(private val actions: OrderSLActions) : OrderEventHandlerData
{
    override val eventHandlers = mapOf(
        OrderEventType.CHANGED_SL to actions.onSLChange

    )
    override val finishEventTypes = setOf(
        OrderEventType.CHANGED_SL,
        OrderEventType.CHANGE_REJECTED
    )
    override val completeEventTypes = finishEventTypes.minus(OrderEventType.CHANGE_REJECTED)
    override val rejectEventType = OrderEventType.CHANGE_REJECTED
    override var retryCall: KRunnable = {}
    override val taskActions = actions.taskActions
    override val type = OrderEventHandlerType.CHANGE
}