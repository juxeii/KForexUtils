package com.jforex.kforexutils.order.event.handler.data

import com.jforex.kforexutils.misc.KRunnable
import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.event.handler.OrderEventHandlerType

data class SetAmountEventData(
    private val actions: OrderAmountActions,
    override val retryCall: KRunnable
) : OrderEventData {
    override val eventHandlers = mapOf(
        OrderEventType.CHANGED_AMOUNT to actions.onAmountChange
    )
    override val finishEventTypes = setOf(
        OrderEventType.CHANGED_AMOUNT,
        OrderEventType.CHANGE_REJECTED
    )
    override val rejectEventType = OrderEventType.CHANGE_REJECTED
    override val taskData = actions.taskData
    override val type = OrderEventHandlerType.CHANGE
}