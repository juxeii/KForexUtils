package com.jforex.kforexutils.order.event.handler.data

import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.event.handler.OrderEventHandlerType
import com.jforex.kforexutils.order.params.OrderRetryParams
import com.jforex.kforexutils.order.params.actions.OrderCommentActions

data class SetCommentEventHandlerData(
    private val actions: OrderCommentActions,
    val retryParamsEx: OrderRetryParams
) :
    OrderEventHandlerData {
    override val eventHandlers = mapOf(
        OrderEventType.CHANGED_COMMENT to actions.onCommentChange,
        OrderEventType.CHANGE_REJECTED to actions.onReject
    )
    override val finishEventTypes = setOf(
        OrderEventType.CHANGED_COMMENT,
        OrderEventType.CHANGE_REJECTED
    )
    override val rejectEventTypes = setOf(
        OrderEventType.CHANGE_REJECTED
    )
    override val basicActions = actions.basicActions
    override val type = OrderEventHandlerType.CHANGE_COMMENT
    override val retryParams = retryParamsEx
}