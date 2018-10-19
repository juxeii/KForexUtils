package com.jforex.kforexutils.order.event.handler.data

import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.event.handler.OrderEventHandlerType
import com.jforex.kforexutils.order.params.OrderRetryParams
import com.jforex.kforexutils.order.params.actions.OrderTPActions

data class SetTPEventHandlerData(
    private val actions: OrderTPActions,
    val retryParamsEx: OrderRetryParams
) :
    OrderEventHandlerData {
    override val eventHandlers = mapOf(
        OrderEventType.CHANGED_TP to actions.onTPChange,
        OrderEventType.CHANGE_REJECTED to actions.onReject
    )
    override val finishEventTypes = setOf(
        OrderEventType.CHANGED_TP,
        OrderEventType.CHANGE_REJECTED
    )
    override val basicActions = actions.basicActions
    override val type = OrderEventHandlerType.CHANGE_TP
    override val retryParams = retryParamsEx
}