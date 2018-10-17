package com.jforex.kforexutils.order.params.actions.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.order.params.actions.OrderCommentActions

@OrderDsl
class OrderCommentActionsBuilder : OrderBasicActionsBuilderBase()
{
    var onCommentChange = emptyOrderEventConsumer
    var onReject = emptyOrderEventConsumer

    fun build() = OrderCommentActions(
        basicActions = basicActions,
        onCommentChange = onCommentChange,
        onReject = onReject
    )

    companion object
    {
        operator fun invoke(block: OrderCommentActionsBuilder.() -> Unit) = OrderCommentActionsBuilder()
            .apply(block)
            .build()
    }
}