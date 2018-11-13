package com.jforex.kforexutils.order.event.handler.data

import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.event.handler.OrderEventHandlerType

class SetTPEventData : OrderEventData {
    override val finishEventTypes = setOf(
        OrderEventType.CHANGED_TP,
        OrderEventType.CHANGE_REJECTED
    )
    override val rejectEventTypes = setOf(OrderEventType.CHANGE_REJECTED)
    override val handlerType = OrderEventHandlerType.CHANGE
}