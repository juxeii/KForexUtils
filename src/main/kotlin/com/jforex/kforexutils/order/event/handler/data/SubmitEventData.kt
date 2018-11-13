package com.jforex.kforexutils.order.event.handler.data

import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.event.handler.OrderEventHandlerType

class SubmitEventData : OrderEventData
{
    override val finishEventTypes = setOf(
        OrderEventType.FULLY_FILLED,
        OrderEventType.SUBMIT_REJECTED,
        OrderEventType.FILL_REJECTED
    )
    override val rejectEventTypes = setOf(
        OrderEventType.FILL_REJECTED,
        OrderEventType.SUBMIT_REJECTED
    )
    override val handlerType = OrderEventHandlerType.SUBMIT
}