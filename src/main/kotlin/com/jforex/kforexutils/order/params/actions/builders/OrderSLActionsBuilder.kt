package com.jforex.kforexutils.order.params.actions.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.order.params.actions.OrderSLActions

@OrderDsl
class OrderSLActionsBuilder : OrderBasicActionsBuilderBase()
{
    var onSLChange = emptyOrderEventConsumer
    var onReject = emptyOrderEventConsumer

    fun build() = OrderSLActions(
        basicActions = basicActions,
        onSLChange = onSLChange
    )

    companion object
    {
        operator fun invoke(block: OrderSLActionsBuilder.() -> Unit) = OrderSLActionsBuilder()
            .apply(block)
            .build()
    }
}