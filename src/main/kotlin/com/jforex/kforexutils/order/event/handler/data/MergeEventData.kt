package com.jforex.kforexutils.order.event.handler.data

import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.event.handler.OrderEventHandlerType

class MergeEventData : OrderEventData
{
    override val allEventTypes = setOf(
        OrderEventType.MERGE_OK,
        OrderEventType.MERGE_CLOSE_OK,
        OrderEventType.MERGE_REJECTED
    )
    override val finishEventTypes = allEventTypes
    override val handlerType = OrderEventHandlerType.MERGE
}