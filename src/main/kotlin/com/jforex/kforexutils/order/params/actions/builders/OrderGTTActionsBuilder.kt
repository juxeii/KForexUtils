package com.jforex.kforexutils.order.params.actions.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.order.params.actions.OrderGTTActions

@OrderDsl
class OrderGTTActionsBuilder : OrderActionsBuilderBase()
{
    var onGTTChange = emptyOrderEventConsumer
    var onReject = emptyOrderEventConsumer

    fun build() = OrderGTTActions(
        basicActions = basicActions,
        onGTTChange = onGTTChange,
        onReject = onReject
    )

    companion object
    {
        operator fun invoke(block: OrderGTTActionsBuilder.() -> Unit) = OrderGTTActionsBuilder()
            .apply(block)
            .build()
    }
}