package com.jforex.kforexutils.order.event.handler.data

import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.event.handler.OrderEventHandlerType
import com.jforex.kforexutils.order.params.actions.OrderCommentActions

data class SetCommentEventHandlerData(private val actions: OrderCommentActions) : OrderEventHandlerData
{
    override val eventHandlers = mapOf(
        OrderEventType.CHANGED_COMMENT to actions.onCommentChange
    )
    override val finishEventTypes = setOf(
        OrderEventType.CHANGED_COMMENT,
        OrderEventType.CHANGE_REJECTED
    )
    override val rejectEventType = OrderEventType.CHANGE_REJECTED
    override val basicActions = actions.basicActions
    override val type = OrderEventHandlerType.CHANGE_COMMENT
    override val rejectEventHandler = actions.onReject
}