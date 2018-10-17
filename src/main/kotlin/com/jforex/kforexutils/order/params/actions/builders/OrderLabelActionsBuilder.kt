package com.jforex.kforexutils.order.params.actions.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.order.params.actions.OrderLabelActions

@OrderDsl
class OrderLabelActionsBuilder : OrderActionsBuilderBase()
{
    var onLabelChange = emptyOrderEventConsumer
    var onReject = emptyOrderEventConsumer

    fun build() = OrderLabelActions(
        basicActions = basicActions,
        onLabelChange = onLabelChange,
        onReject = onReject
    )

    companion object
    {
        operator fun invoke(block: OrderLabelActionsBuilder.() -> Unit) = OrderLabelActionsBuilder()
            .apply(block)
            .build()
    }
}