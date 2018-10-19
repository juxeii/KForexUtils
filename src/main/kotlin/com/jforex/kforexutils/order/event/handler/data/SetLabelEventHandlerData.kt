package com.jforex.kforexutils.order.event.handler.data

import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.event.handler.OrderEventHandlerType
import com.jforex.kforexutils.order.params.OrderRetryParams
import com.jforex.kforexutils.order.params.actions.OrderLabelActions

data class SetLabelEventHandlerData(
    private val actions: OrderLabelActions,
    val retryParamsEx: OrderRetryParams
) :
    OrderEventHandlerData {
    override val eventHandlers = mapOf(
        OrderEventType.CHANGED_LABEL to actions.onLabelChange,
        OrderEventType.CHANGE_REJECTED to actions.onReject
    )
    override val finishEventTypes = setOf(
        OrderEventType.CHANGED_LABEL,
        OrderEventType.CHANGE_REJECTED
    )
    override val rejectEventTypes = setOf(
        OrderEventType.CHANGE_REJECTED
    )
    override val basicActions = actions.basicActions
    override val type = OrderEventHandlerType.CHANGE_LABEL
    override val retryParams = retryParamsEx
}