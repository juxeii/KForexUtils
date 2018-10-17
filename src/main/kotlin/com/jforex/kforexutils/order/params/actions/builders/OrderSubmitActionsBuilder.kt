package com.jforex.kforexutils.order.params.actions.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.order.params.actions.OrderSubmitActions

@OrderDsl
class OrderSubmitActionsBuilder : OrderActionsBuilderBase()
{
    var onSubmit = emptyOrderEventConsumer
    var onPartialFill = emptyOrderEventConsumer
    var onFullFill = emptyOrderEventConsumer
    var onSubmitReject = emptyOrderEventConsumer
    var onFillReject = emptyOrderEventConsumer

    fun build() = OrderSubmitActions(
        basicActions,
        onSubmit,
        onPartialFill,
        onFullFill,
        onSubmitReject,
        onFillReject
    )

    companion object
    {
        operator fun invoke(block: OrderSubmitActionsBuilder.() -> Unit) =
            OrderSubmitActionsBuilder()
                .apply(block)
                .build()
    }
}