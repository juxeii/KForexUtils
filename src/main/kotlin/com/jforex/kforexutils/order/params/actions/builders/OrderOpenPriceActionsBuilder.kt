package com.jforex.kforexutils.order.params.actions.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.order.params.actions.OrderOpenPriceActions

@OrderDsl
class OrderOpenPriceActionsBuilder : OrderTaskActionsBuilderBase()
{
    var onOpenPriceChange = emptyOrderEventConsumer

    private fun build() = OrderOpenPriceActions(
        taskData = taskActions,
        onOpenPriceChange = onOpenPriceChange
    )

    companion object
    {
        operator fun invoke(block: OrderOpenPriceActionsBuilder.() -> Unit) = OrderOpenPriceActionsBuilder()
            .apply(block)
            .build()
    }
}