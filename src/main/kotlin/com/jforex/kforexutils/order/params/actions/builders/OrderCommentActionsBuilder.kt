package com.jforex.kforexutils.order.params.actions.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.order.params.actions.OrderCommentActions

@OrderDsl
class OrderCommentActionsBuilder : OrderTaskActionsBuilderBase()
{
    var onCommentChange = emptyOrderEventConsumer
    var onReject = emptyOrderEventConsumer

    private fun build() = OrderCommentActions(
        taskActions = taskActions,
        onCommentChange = onCommentChange
    )

    companion object
    {
        operator fun invoke(block: OrderCommentActionsBuilder.() -> Unit) = OrderCommentActionsBuilder()
            .apply(block)
            .build()
    }
}