package com.jforex.kforexutils.order.event.consumer.data

import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.event.consumer.OrderEventConsumerType
import com.jforex.kforexutils.order.params.actions.OrderBasicActions
import com.jforex.kforexutils.order.params.actions.OrderTPActions

data class SetTPEventConsumerData(private val tpActions: OrderTPActions) :
    OrderEventConsumerData
{
    override val eventHandlers = mapOf(
        OrderEventType.CHANGED_TP to tpActions.onTPChange,
        OrderEventType.CHANGE_REJECTED to tpActions.onReject
    )
    override val finishEventTypes = setOf(
        OrderEventType.CHANGED_TP,
        OrderEventType.CHANGE_REJECTED
    )
    override val basicActions: OrderBasicActions
        get() = tpActions.basicActions
    override val type: OrderEventConsumerType
        get() = OrderEventConsumerType.CHANGE_TP
}