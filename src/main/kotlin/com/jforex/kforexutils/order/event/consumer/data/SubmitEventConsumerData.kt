package com.jforex.kforexutils.order.event.consumer.data

import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.event.consumer.OrderEventConsumerType
import com.jforex.kforexutils.order.params.actions.OrderSubmitActions

data class SubmitEventConsumerData(private val submitActions: OrderSubmitActions) :
    OrderEventConsumerData {
    override val eventHandlers = mapOf(
        OrderEventType.SUBMIT_OK to submitActions.onSubmit,
        OrderEventType.PARTIALLY_FILLED to submitActions.onPartialFill,
        OrderEventType.FULLY_FILLED to submitActions.onFullFill,
        OrderEventType.SUBMIT_REJECTED to submitActions.onSubmitReject,
        OrderEventType.FILL_REJECTED to submitActions.onFillReject
    )
    override val finishEventTypes = setOf(
        OrderEventType.FULLY_FILLED,
        OrderEventType.SUBMIT_REJECTED,
        OrderEventType.FILL_REJECTED
    )
    override val basicActions = submitActions.basicActions
    override val type: OrderEventConsumerType
        get() = OrderEventConsumerType.SUBMIT
}