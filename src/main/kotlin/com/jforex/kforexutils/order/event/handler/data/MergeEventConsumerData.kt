package com.jforex.kforexutils.order.event.handler.data

import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.event.handler.OrderEventHandlerType
import com.jforex.kforexutils.order.params.actions.OrderMergeActions

data class MergeEventConsumerData(private val actions: OrderMergeActions) :
    OrderEventConsumerData {
    override val eventHandlers = mapOf(
        OrderEventType.MERGE_OK to actions.onMerge,
        OrderEventType.MERGE_CLOSE_OK to actions.onMergeClose,
        OrderEventType.MERGE_REJECTED to actions.onReject
    )
    override val finishEventTypes = setOf(
        OrderEventType.MERGE_OK,
        OrderEventType.MERGE_CLOSE_OK,
        OrderEventType.MERGE_REJECTED
    )
    override val basicActions = actions.basicActions
    override val type: OrderEventHandlerType
        get() = OrderEventHandlerType.MERGE
}