package com.jforex.kforexutils.order.params.actions.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.order.params.actions.OrderLabelActions

@OrderDsl
class OrderLabelActionsBuilder : OrderBasicActionsBuilderBase()
{
    var onLabelChange = emptyOrderEventConsumer
    var onReject = emptyOrderEventConsumer

    private fun build() = OrderLabelActions(
        basicActions = basicActions,
        onLabelChange = onLabelChange
    )

    companion object
    {
        operator fun invoke(block: OrderLabelActionsBuilder.() -> Unit) = OrderLabelActionsBuilder()
            .apply(block)
            .build()
    }
}