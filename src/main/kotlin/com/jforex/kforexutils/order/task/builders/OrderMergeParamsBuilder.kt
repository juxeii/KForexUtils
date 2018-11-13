package com.jforex.kforexutils.order.task.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.misc.emptyOrderCallParams
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.event.handler.data.MergeEventData
import com.jforex.kforexutils.order.task.OrderTaskEventParams
import com.jforex.kforexutils.order.task.OrderTaskParams

@OrderDsl
class OrderMergeParamsBuilder
{
    private var callParams = emptyOrderCallParams
    var onMerge = emptyOrderEventConsumer
    var onMergeClose = emptyOrderEventConsumer
    var onMergeReject = emptyOrderEventConsumer
    private val eventHandlers = mapOf(
        OrderEventType.MERGE_OK to onMerge,
        OrderEventType.MERGE_CLOSE_OK to onMergeClose,
        OrderEventType.MERGE_REJECTED to onMergeReject
    )

    fun callParams(block: OrderCallParamsBuilder.() -> Unit)
    {
        callParams = OrderCallParamsBuilder(block)
    }

    private fun build() = OrderTaskParams(
        callParams = callParams,
        eventParams = OrderTaskEventParams(
            eventData = MergeEventData(),
            eventHandlers = eventHandlers
        )
    )

    companion object
    {
        operator fun invoke(block: OrderMergeParamsBuilder.() -> Unit) =
            OrderMergeParamsBuilder()
                .apply(block)
                .build()
    }
}