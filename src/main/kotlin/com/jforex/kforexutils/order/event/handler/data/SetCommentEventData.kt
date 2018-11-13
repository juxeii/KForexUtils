package com.jforex.kforexutils.order.event.handler.data

import com.jforex.kforexutils.misc.KRunnable
import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.event.handler.OrderEventHandlerType
import com.jforex.kforexutils.order.params.actions.OrderCommentActions

data class SetCommentEventData(
    private val actions: OrderCommentActions,
    override val retryCall: KRunnable
) : OrderEventData {
    override val eventHandlers = mapOf(
        OrderEventType.CHANGED_COMMENT to actions.onCommentChange
    )
    override val finishEventTypes = setOf(
        OrderEventType.CHANGED_COMMENT,
        OrderEventType.CHANGE_REJECTED
    )
    override val rejectEventType = OrderEventType.CHANGE_REJECTED
    override val taskData = actions.taskData
    override val type = OrderEventHandlerType.CHANGE
}