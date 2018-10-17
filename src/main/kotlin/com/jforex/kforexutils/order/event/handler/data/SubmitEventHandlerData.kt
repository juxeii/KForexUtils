package com.jforex.kforexutils.order.event.handler.data

import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.event.handler.OrderEventHandlerType
import com.jforex.kforexutils.order.params.actions.OrderSubmitActions

data class SubmitEventHandlerData(private val actions: OrderSubmitActions) :
    OrderEventHandlerData
{
    override val eventHandlers = mapOf(
        OrderEventType.SUBMIT_OK to actions.onSubmit,
        OrderEventType.PARTIALLY_FILLED to actions.onPartialFill,
        OrderEventType.FULLY_FILLED to actions.onFullFill,
        OrderEventType.SUBMIT_REJECTED to actions.onSubmitReject,
        OrderEventType.FILL_REJECTED to actions.onFillReject
    )
    override val finishEventTypes = setOf(
        OrderEventType.FULLY_FILLED,
        OrderEventType.SUBMIT_REJECTED,
        OrderEventType.FILL_REJECTED
    )
    override val basicActions = actions.basicActions
    override val type = OrderEventHandlerType.SUBMIT
}