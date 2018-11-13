package com.jforex.kforexutils.order.event.handler.data

import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.event.handler.OrderEventHandlerType

class ChangeEventData(changedType: OrderEventType) : OrderEventData
{
    override val finishEventTypes = setOf(
        changedType,
        OrderEventType.CHANGE_REJECTED
    )
    override val rejectEventTypes = setOf(OrderEventType.CHANGE_REJECTED)
    override val handlerType = OrderEventHandlerType.CHANGE
}