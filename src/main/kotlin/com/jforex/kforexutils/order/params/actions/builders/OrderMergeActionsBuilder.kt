package com.jforex.kforexutils.order.params.actions.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.misc.OrderEventConsumer
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.order.params.actions.OrderBasicActions
import com.jforex.kforexutils.order.params.actions.OrderMergeActions

@OrderDsl
class OrderMergeActionsBuilder {
    var basicActions: OrderBasicActions = OrderBasicActions()
    var onMerge: OrderEventConsumer = emptyOrderEventConsumer
    var onMergeClose: OrderEventConsumer = emptyOrderEventConsumer
    var onReject: OrderEventConsumer = emptyOrderEventConsumer

    fun basicActions(block: OrderBasicActionsBuilder.() -> Unit) {
        basicActions = OrderBasicActionsBuilder()
            .apply(block)
            .build()

    }

    fun build() = OrderMergeActions(
        basicActions = basicActions,
        onMerge = onMerge,
        onMergeClose = onMergeClose,
        onReject = onReject
    )

    companion object {
        operator fun invoke(block: OrderMergeActionsBuilder.() -> Unit) = OrderMergeActionsBuilder()
            .apply(block)
            .build()
    }
}