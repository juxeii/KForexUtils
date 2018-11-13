package com.jforex.kforexutils.order.params.actions.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.order.params.actions.OrderGTTActions

@OrderDsl
class OrderGTTActionsBuilder : OrderTaskActionsBuilderBase()
{
    var onGTTChange = emptyOrderEventConsumer

    private fun build() = OrderGTTActions(
        taskData = taskActions,
        onGTTChange = onGTTChange
    )

    companion object
    {
        operator fun invoke(block: OrderGTTActionsBuilder.() -> Unit) = OrderGTTActionsBuilder()
            .apply(block)
            .build()
    }
}