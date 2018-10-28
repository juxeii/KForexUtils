package com.jforex.kforexutils.order.params.actions.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.order.params.actions.OrderAmountActions

@OrderDsl
class OrderAmountActionsBuilder : OrderBasicActionsBuilderBase()
{
    var onAmountChange = emptyOrderEventConsumer
    var onReject = emptyOrderEventConsumer

    private fun build() = OrderAmountActions(
        basicActions = basicActions,
        onAmountChange = onAmountChange
    )

    companion object
    {
        operator fun invoke(block: OrderAmountActionsBuilder.() -> Unit) = OrderAmountActionsBuilder()
            .apply(block)
            .build()
    }
}