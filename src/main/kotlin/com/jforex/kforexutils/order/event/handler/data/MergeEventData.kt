package com.jforex.kforexutils.order.event.handler.data

import com.jforex.kforexutils.misc.KRunnable
import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.event.handler.OrderEventHandlerType
import com.jforex.kforexutils.order.params.actions.OrderMergeActions

data class MergeEventData(
    private val actions: OrderMergeActions,
    override val retryCall: KRunnable
) : OrderEventData {
    override val eventHandlers = mapOf(
        OrderEventType.MERGE_OK to actions.onMerge,
        OrderEventType.MERGE_CLOSE_OK to actions.onMergeClose
    )
    override val finishEventTypes = setOf(
        OrderEventType.MERGE_OK,
        OrderEventType.MERGE_CLOSE_OK,
        OrderEventType.MERGE_REJECTED
    )
    override val rejectEventType = OrderEventType.MERGE_REJECTED
    override val taskData = actions.taskData
    override val type = OrderEventHandlerType.MERGE
}