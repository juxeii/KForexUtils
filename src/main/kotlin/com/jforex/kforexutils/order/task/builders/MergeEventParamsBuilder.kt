package com.jforex.kforexutils.order.task.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.event.handler.data.MergeEventData
import com.jforex.kforexutils.order.task.OrderEventHandlerParams

@OrderDsl
class MergeEventParamsBuilder : IParamsBuilder<MergeEventParamsBuilder>
{
    var onMerge = emptyOrderEventConsumer
    var onMergeClose = emptyOrderEventConsumer
    var onMergeReject = emptyOrderEventConsumer

    private fun getEventHandlerParams() = OrderEventHandlerParams(
        eventData = MergeEventData(),
        eventHandlers = filterEventHandlers(createMap())
    )

    private fun createMap() = mapOf(
        OrderEventType.MERGE_OK to onMerge,
        OrderEventType.MERGE_CLOSE_OK to onMergeClose,
        OrderEventType.MERGE_REJECTED to onMergeReject
    )

    override fun build(block: MergeEventParamsBuilder.() -> Unit) = apply(block).getEventHandlerParams()
}