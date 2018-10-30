package com.jforex.kforexutils.order.params.actions.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.order.params.actions.OrderAmountActions

@OrderDsl
class OrderAmountActionsBuilder : OrderTaskActionsBuilderBase()
{
    var onAmountChange = emptyOrderEventConsumer

    private fun build() = OrderAmountActions(
        taskActions = taskActions,
        onAmountChange = onAmountChange
    )

    companion object
    {
        operator fun invoke(block: OrderAmountActionsBuilder.() -> Unit) = OrderAmountActionsBuilder()
            .apply(block)
            .build()
    }
}