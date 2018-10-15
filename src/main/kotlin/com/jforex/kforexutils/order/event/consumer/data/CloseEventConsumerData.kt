package com.jforex.kforexutils.order.event.consumer.data

import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.event.consumer.OrderEventHandlerType
import com.jforex.kforexutils.order.params.actions.OrderBasicActions
import com.jforex.kforexutils.order.params.actions.OrderCloseActions

data class CloseEventConsumerData(private val closeActions: OrderCloseActions) :
    OrderEventConsumerData
{
    override val eventHandlers = mapOf(
        OrderEventType.CLOSE_OK to closeActions.onClose,
        OrderEventType.PARTIAL_CLOSE_OK to closeActions.onPartialClose,
        OrderEventType.CLOSE_REJECTED to closeActions.onReject
    )
    override val finishEventTypes = setOf(
        OrderEventType.CLOSE_OK,
        OrderEventType.PARTIAL_CLOSE_OK,
        OrderEventType.CLOSE_REJECTED
    )
    override val basicActions: OrderBasicActions
        get() = closeActions.basicActions
    override val type: OrderEventHandlerType
        get() = OrderEventHandlerType.CLOSE
}