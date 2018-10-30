package com.jforex.kforexutils.order.params.actions.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.order.params.actions.OrderTPActions

@OrderDsl
class OrderTPActionsBuilder : OrderTaskActionsBuilderBase()
{
    var onTPChange = emptyOrderEventConsumer

    private fun build() = OrderTPActions(
        taskActions = taskActions,
        onTPChange = onTPChange
    )

    companion object
    {
        operator fun invoke(block: OrderTPActionsBuilder.() -> Unit) = OrderTPActionsBuilder()
            .apply(block)
            .build()
    }
}