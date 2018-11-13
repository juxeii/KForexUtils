package com.jforex.kforexutils.order.event.handler.data

import com.jforex.kforexutils.misc.KRunnable
import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.event.handler.OrderEventHandlerType
import com.jforex.kforexutils.order.params.actions.OrderLabelActions

data class SetLabelEventData(
    private val actions: OrderLabelActions,
    override val retryCall: KRunnable
) : OrderEventData {
    override val eventHandlers = mapOf(
        OrderEventType.CHANGED_LABEL to actions.onLabelChange
    )
    override val finishEventTypes = setOf(
        OrderEventType.CHANGED_LABEL,
        OrderEventType.CHANGE_REJECTED
    )
    override val rejectEventType = OrderEventType.CHANGE_REJECTED
    override val taskData = actions.taskData
    override val type = OrderEventHandlerType.CHANGE
}