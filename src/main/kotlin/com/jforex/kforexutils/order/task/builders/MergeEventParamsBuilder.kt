package com.jforex.kforexutils.order.task.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.order.event.OrderEventType

@OrderDsl
class MergeEventParamsBuilder : CommonEventParamsBuilder()
{
    var onMerge = emptyOrderEventConsumer
    var onMergeClose = emptyOrderEventConsumer
    var onMergeReject = emptyOrderEventConsumer

    override fun createMap() = mapOf(
        OrderEventType.MERGE_OK to onMerge,
        OrderEventType.MERGE_CLOSE_OK to onMergeClose,
        OrderEventType.MERGE_REJECTED to onMergeReject
    )

    companion object {
        operator fun invoke(block: MergeEventParamsBuilder.() -> Unit = {}) = MergeEventParamsBuilder()
            .apply(block)
            .build()
    }
}