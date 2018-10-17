package com.jforex.kforexutils.order.event.handler.data

import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.event.handler.OrderEventHandlerType
import com.jforex.kforexutils.order.params.actions.OrderCloseActions

data class CloseEventHandlerData(private val actions: OrderCloseActions) :
    OrderEventHandlerData
{
    override val eventHandlers = mapOf(
        OrderEventType.CLOSE_OK to actions.onClose,
        OrderEventType.PARTIAL_CLOSE_OK to actions.onPartialClose,
        OrderEventType.CLOSE_REJECTED to actions.onReject
    )
    override val finishEventTypes = setOf(
        OrderEventType.CLOSE_OK,
        OrderEventType.PARTIAL_CLOSE_OK,
        OrderEventType.CLOSE_REJECTED
    )
    override val basicActions = actions.basicActions
    override val type = OrderEventHandlerType.CLOSE
}