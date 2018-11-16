package com.jforex.kforexutils.order.task.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.misc.emptyCallActions
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.event.handler.data.ChangeEventData
import com.jforex.kforexutils.order.task.OrderEventHandlerParams
import com.jforex.kforexutils.order.task.OrderTaskParams

@OrderDsl
class OrderCommentParamsBuilder
{
    private var callActions = emptyCallActions
    var onCommentChange = emptyOrderEventConsumer
    var onCommentReject = emptyOrderEventConsumer

    fun callActions(block: OrderCallActionsBuilder.() -> Unit)
    {
        callActions = OrderCallActionsBuilder(block)
    }

    private fun build() = OrderTaskParams(
        callActions = callActions,
        eventHandlerParams = OrderEventHandlerParams(
            eventData = ChangeEventData(OrderEventType.CHANGED_COMMENT),
            eventHandlers = mapOf(
                OrderEventType.CHANGED_COMMENT to onCommentChange,
                OrderEventType.CHANGE_REJECTED to onCommentReject
            )
        )
    )

    companion object
    {
        operator fun invoke(block: OrderCommentParamsBuilder.() -> Unit) =
            OrderCommentParamsBuilder()
                .apply(block)
                .build()
    }
}