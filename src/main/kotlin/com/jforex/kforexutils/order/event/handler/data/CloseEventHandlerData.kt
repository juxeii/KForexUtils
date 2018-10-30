package com.jforex.kforexutils.order.event.handler.data

import com.jforex.kforexutils.misc.KRunnable
import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.event.handler.OrderEventHandlerType
import com.jforex.kforexutils.order.params.actions.OrderCloseActions

data class CloseEventHandlerData(
    private val actions: OrderCloseActions
) :
    OrderEventHandlerData
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
    override val completeEventTypes = finishEventTypes.minus(OrderEventType.CLOSE_REJECTED)
    override val rejectEventType = OrderEventType.CLOSE_REJECTED
    override var retryCall: KRunnable = {}
    override val taskActions = actions.taskActions
    override val type = OrderEventHandlerType.CLOSE
}