package com.jforex.kforexutils.order.params.actions.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.order.params.actions.OrderMergeActions

@OrderDsl
class OrderMergeActionsBuilder : OrderBasicActionsBuilderBase()
{
    var onMerge = emptyOrderEventConsumer
    var onMergeClose = emptyOrderEventConsumer
    var onReject = emptyOrderEventConsumer

    fun build() = OrderMergeActions(
        basicActions = basicActions,
        onMerge = onMerge,
        onMergeClose = onMergeClose
    )

    companion object
    {
        operator fun invoke(block: OrderMergeActionsBuilder.() -> Unit) = OrderMergeActionsBuilder()
            .apply(block)
            .build()
    }
}