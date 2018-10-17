package com.jforex.kforexutils.order.event.handler.data

import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.event.handler.OrderEventHandlerType
import com.jforex.kforexutils.order.params.actions.OrderAmountActions

data class SetAmountEventHandlerData(private val actions: OrderAmountActions) :
    OrderEventHandlerData
{
    override val eventHandlers = mapOf(
        OrderEventType.CHANGED_AMOUNT to actions.onAmountChange,
        OrderEventType.CHANGE_REJECTED to actions.onReject
    )
    override val finishEventTypes = setOf(
        OrderEventType.CHANGED_AMOUNT,
        OrderEventType.CHANGE_REJECTED
    )
    override val basicActions = actions.basicActions
    override val type = OrderEventHandlerType.CHANGE_AMOUNT
}