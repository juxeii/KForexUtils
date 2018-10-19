package com.jforex.kforexutils.order.event.handler.data

import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.event.handler.OrderEventHandlerType
import com.jforex.kforexutils.order.params.OrderRetryParams
import com.jforex.kforexutils.order.params.actions.OrderMergeActions

data class MergeEventHandlerData(
    private val actions: OrderMergeActions,
    val retryParamsEx: OrderRetryParams
) :
    OrderEventHandlerData {
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
    override val rejectEventTypes = setOf(
        OrderEventType.MERGE_REJECTED
    )
    override val basicActions = actions.basicActions
    override val type = OrderEventHandlerType.MERGE
    override val retryParams = retryParamsEx
}