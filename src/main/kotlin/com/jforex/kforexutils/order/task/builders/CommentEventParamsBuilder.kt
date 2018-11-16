package com.jforex.kforexutils.order.task.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.event.handler.data.ChangeEventData
import com.jforex.kforexutils.order.task.OrderEventHandlerParams

@OrderDsl
class CommentEventParamsBuilder : IParamsBuilder<CommentEventParamsBuilder>
{
    var onCommentChange = emptyOrderEventConsumer
    var onCommentReject = emptyOrderEventConsumer

    private fun getEventHandlerParams() = OrderEventHandlerParams(
        eventData = ChangeEventData(OrderEventType.CHANGED_COMMENT),
        eventHandlers = mapOf(
            OrderEventType.CHANGED_COMMENT to onCommentChange,
            OrderEventType.CHANGE_REJECTED to onCommentReject
        )
    )

    override fun build(block: CommentEventParamsBuilder.() -> Unit) = apply(block).getEventHandlerParams()
}