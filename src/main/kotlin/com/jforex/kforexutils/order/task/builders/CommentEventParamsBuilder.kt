package com.jforex.kforexutils.order.task.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.order.event.OrderEventType

@OrderDsl
class CommentEventParamsBuilder : CommonEventParamsBuilder()
{
    var onCommentChange = emptyOrderEventConsumer
    var onCommentReject = emptyOrderEventConsumer

    override fun createMap() = mapOf(
        OrderEventType.CHANGED_COMMENT to onCommentChange,
        OrderEventType.CHANGE_REJECTED to onCommentReject
    )

    companion object {
        operator fun invoke(block: CommentEventParamsBuilder.() -> Unit = {}) = CommentEventParamsBuilder()
            .apply(block)
            .build()
    }
}