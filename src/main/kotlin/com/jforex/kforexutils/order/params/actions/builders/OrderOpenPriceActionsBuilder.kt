package com.jforex.kforexutils.order.params.actions.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.order.params.actions.OrderOpenPriceActions

@OrderDsl
class OrderOpenPriceActionsBuilder : OrderBasicActionsBuilderBase()
{
    var onOpenPriceChange = emptyOrderEventConsumer
    var onReject = emptyOrderEventConsumer

    private fun build() = OrderOpenPriceActions(
        basicActions = basicActions,
        onOpenPriceChange = onOpenPriceChange
    )

    companion object
    {
        operator fun invoke(block: OrderOpenPriceActionsBuilder.() -> Unit) = OrderOpenPriceActionsBuilder()
            .apply(block)
            .build()
    }
}