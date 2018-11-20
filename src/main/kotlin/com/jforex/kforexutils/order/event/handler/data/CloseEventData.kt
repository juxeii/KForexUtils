package com.jforex.kforexutils.order.event.handler.data

import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.event.handler.OrderEventHandlerType

class CloseEventData : OrderEventData
{
    override val allEventTypes = setOf(
        OrderEventType.CLOSE_OK,
        OrderEventType.PARTIAL_CLOSE_OK,
        OrderEventType.CLOSE_REJECTED
    )
    override val finishEventTypes = allEventTypes
    override val handlerType = OrderEventHandlerType.CLOSE
}