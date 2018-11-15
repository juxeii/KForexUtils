package com.jforex.kforexutils.order.task.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.misc.emptyOrderCallParams
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.event.handler.data.ChangeEventData
import com.jforex.kforexutils.order.task.OrderTaskEventParams
import com.jforex.kforexutils.order.task.OrderTaskParams

@OrderDsl
class OrderCommentParamsBuilder
{
    private var callParams = emptyOrderCallParams
    var onCommentChange = emptyOrderEventConsumer
    var onCommentReject = emptyOrderEventConsumer
    private val eventHandlers = mapOf(
        OrderEventType.CHANGED_COMMENT to onCommentChange,
        OrderEventType.CHANGE_REJECTED to onCommentReject
    )

    fun callParams(block: OrderCallParamsBuilder.() -> Unit)
    {
        callParams = OrderCallParamsBuilder(block)
    }

    private fun build() = OrderTaskParams(
        callParams = callParams,
        eventParams = OrderTaskEventParams(
            eventData = ChangeEventData(OrderEventType.CHANGED_COMMENT),
            eventHandlers = eventHandlers
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