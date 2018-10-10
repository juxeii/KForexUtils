package com.jforex.kforexutils.order.params.actions.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.misc.OrderEventConsumer
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.order.params.actions.OrderBasicActions
import com.jforex.kforexutils.order.params.actions.OrderSubmitActions

@OrderDsl
class OrderSubmitActionsBuilder
{
    var basicActions: OrderBasicActions =
        OrderBasicActions()
    var onSubmit: OrderEventConsumer = emptyOrderEventConsumer
    var onPartialFill: OrderEventConsumer = emptyOrderEventConsumer
    var onFullFill: OrderEventConsumer = emptyOrderEventConsumer
    var onSubmitReject: OrderEventConsumer = emptyOrderEventConsumer
    var onFillReject: OrderEventConsumer = emptyOrderEventConsumer

    fun basicActions(block: OrderBasicActionsBuilder.() -> Unit)
    {
        basicActions = OrderBasicActionsBuilder()
            .apply(block)
            .build()
    }

    fun build() = OrderSubmitActions(
        basicActions,
        onSubmit,
        onPartialFill,
        onFullFill,
        onSubmitReject,
        onFillReject
    )
}