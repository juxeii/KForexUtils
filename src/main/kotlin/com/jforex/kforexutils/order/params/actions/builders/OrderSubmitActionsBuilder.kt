package com.jforex.kforexutils.order.params.actions.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.order.params.actions.OrderSubmitActions

@OrderDsl
class OrderSubmitActionsBuilder : OrderTaskActionsBuilderBase()
{
    var onSubmit = emptyOrderEventConsumer
    var onPartialFill = emptyOrderEventConsumer
    var onFullFill = emptyOrderEventConsumer

    private fun build() = OrderSubmitActions(
        taskData = taskActions,
        onSubmit = onSubmit,
        onPartialFill = onPartialFill,
        onFullFill = onFullFill
    )

    companion object
    {
        operator fun invoke(block: OrderSubmitActionsBuilder.() -> Unit) =
            OrderSubmitActionsBuilder()
                .apply(block)
                .build()
    }
}