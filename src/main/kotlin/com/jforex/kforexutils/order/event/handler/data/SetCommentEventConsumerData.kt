package com.jforex.kforexutils.order.event.handler.data

import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.event.handler.OrderEventHandlerType
import com.jforex.kforexutils.order.params.actions.OrderBasicActions
import com.jforex.kforexutils.order.params.actions.OrderCommentActions

data class SetCommentEventConsumerData(private val actions: OrderCommentActions) :
    OrderEventConsumerData {
    override val eventHandlers = mapOf(
        OrderEventType.CHANGED_COMMENT to actions.onCommentChange,
        OrderEventType.CHANGE_REJECTED to actions.onReject
    )
    override val finishEventTypes = setOf(
        OrderEventType.CHANGED_COMMENT,
        OrderEventType.CHANGE_REJECTED
    )
    override val basicActions: OrderBasicActions
        get() = actions.basicActions
    override val type: OrderEventHandlerType
        get() = OrderEventHandlerType.CHANGE_COMMENT
}