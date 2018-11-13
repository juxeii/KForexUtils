package com.jforex.kforexutils.order.event.handler.data

import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.event.handler.OrderEventHandlerType

class MergeEventData : OrderEventData
{
    override val finishEventTypes = setOf(
        OrderEventType.MERGE_OK,
        OrderEventType.MERGE_CLOSE_OK,
        OrderEventType.MERGE_REJECTED
    )
    override val rejectEventTypes = setOf(OrderEventType.MERGE_REJECTED)
    override val handlerType = OrderEventHandlerType.MERGE
}