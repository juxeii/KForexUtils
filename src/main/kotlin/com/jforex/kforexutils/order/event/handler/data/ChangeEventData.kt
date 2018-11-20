package com.jforex.kforexutils.order.event.handler.data

import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.event.handler.OrderEventHandlerType

class ChangeEventData(changedType: OrderEventType) : OrderEventData {
    override val allEventTypes = setOf(
        changedType,
        OrderEventType.CHANGE_REJECTED
    )
    override val finishEventTypes = allEventTypes
    override val rejectEventTypes = setOf(OrderEventType.CHANGE_REJECTED)
    override val handlerType = OrderEventHandlerType.CHANGE
}