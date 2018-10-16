package com.jforex.kforexutils.order.event.handler.data

import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.event.handler.OrderEventHandlerType
import com.jforex.kforexutils.order.params.actions.OrderAmountActions
import com.jforex.kforexutils.order.params.actions.OrderBasicActions

data class SetAmountEventConsumerData(private val tpActions: OrderAmountActions) :
    OrderEventConsumerData {
    override val eventHandlers = mapOf(
        OrderEventType.CHANGED_AMOUNT to tpActions.onAmountChange,
        OrderEventType.CHANGE_REJECTED to tpActions.onReject
    )
    override val finishEventTypes = setOf(
        OrderEventType.CHANGED_AMOUNT,
        OrderEventType.CHANGE_REJECTED
    )
    override val basicActions: OrderBasicActions
        get() = tpActions.basicActions
    override val type: OrderEventHandlerType
        get() = OrderEventHandlerType.CHANGE_AMOUNT
}