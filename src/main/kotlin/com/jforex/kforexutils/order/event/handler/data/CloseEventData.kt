package com.jforex.kforexutils.order.event.handler.data

import com.jforex.kforexutils.misc.KRunnable
import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.event.handler.OrderEventHandlerType

data class CloseEventData(
    private val actions: OrderCloseActions,
    override val retryCall: KRunnable
) : OrderEventData
{
    override val eventHandlers = mapOf(
        OrderEventType.CLOSE_OK to actions.onClose,
        OrderEventType.PARTIAL_CLOSE_OK to actions.onPartialClose
    )
    override val finishEventTypes = setOf(
        OrderEventType.CLOSE_OK,
        OrderEventType.PARTIAL_CLOSE_OK,
        OrderEventType.CLOSE_REJECTED
    )
    override val rejectEventType = OrderEventType.CLOSE_REJECTED
    override val taskData = actions.taskData
    override val type = OrderEventHandlerType.CLOSE
}