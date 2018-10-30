package com.jforex.kforexutils.order.event.handler.data

import com.jforex.kforexutils.misc.KRunnable
import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.event.handler.OrderEventHandlerType
import com.jforex.kforexutils.order.params.actions.OrderTPActions

data class SetTPEventHandlerData(private val actions: OrderTPActions) : OrderEventHandlerData
{
    override val eventHandlers = mapOf(
        OrderEventType.CHANGED_TP to actions.onTPChange
    )
    override val finishEventTypes = setOf(
        OrderEventType.CHANGED_TP,
        OrderEventType.CHANGE_REJECTED
    )
    override val completeEventTypes = finishEventTypes.minus(OrderEventType.CHANGE_REJECTED)
    override val rejectEventType = OrderEventType.CHANGE_REJECTED
    override var retryCall: KRunnable = {}
    override val taskActions = actions.taskActions
    override val type = OrderEventHandlerType.CHANGE
}