package com.jforex.kforexutils.order.params.actions.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.misc.OrderEventConsumer
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.order.params.actions.OrderBasicActions
import com.jforex.kforexutils.order.params.actions.OrderCommentActions

@OrderDsl
class OrderCommentActionsBuilder {
    var basicActions: OrderBasicActions = OrderBasicActions()
    var onCommentChange: OrderEventConsumer = emptyOrderEventConsumer
    var onReject: OrderEventConsumer = emptyOrderEventConsumer

    fun basicActions(block: OrderBasicActionsBuilder.() -> Unit) {
        basicActions = OrderBasicActionsBuilder()
            .apply(block)
            .build()

    }

    fun build() = OrderCommentActions(
        basicActions = basicActions,
        onCommentChange = onCommentChange,
        onReject = onReject
    )

    companion object {
        operator fun invoke(block: OrderCommentActionsBuilder.() -> Unit) = OrderCommentActionsBuilder()
            .apply(block)
            .build()
    }
}