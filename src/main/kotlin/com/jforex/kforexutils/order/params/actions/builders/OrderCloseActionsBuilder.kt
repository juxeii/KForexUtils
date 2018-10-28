package com.jforex.kforexutils.order.params.actions.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.order.params.actions.OrderCloseActions

@OrderDsl
class OrderCloseActionsBuilder : OrderBasicActionsBuilderBase()
{
    var onClose = emptyOrderEventConsumer
    var onPartialClose = emptyOrderEventConsumer
    var onReject = emptyOrderEventConsumer

    private fun build() = OrderCloseActions(
        basicActions,
        onClose,
        onPartialClose
    )

    companion object
    {
        operator fun invoke(block: OrderCloseActionsBuilder.() -> Unit) =
            OrderCloseActionsBuilder()
                .apply(block)
                .build()
    }
}