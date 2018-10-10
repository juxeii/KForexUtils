package com.jforex.kforexutils.order.params.actions.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.misc.OrderEventConsumer
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.order.params.actions.OrderBasicActions
import com.jforex.kforexutils.order.params.actions.OrderCloseActions

@OrderDsl
class OrderCloseActionsBuilder
{
    var basicActions: OrderBasicActions =
        OrderBasicActions()
    var onClose: OrderEventConsumer = emptyOrderEventConsumer
    var onPartialClose: OrderEventConsumer = emptyOrderEventConsumer
    var onReject: OrderEventConsumer = emptyOrderEventConsumer

    fun basicActions(block: OrderBasicActionsBuilder.() -> Unit)
    {
        basicActions = OrderBasicActionsBuilder()
            .apply(block)
            .build()
    }

    fun build() = OrderCloseActions(
        basicActions,
        onClose,
        onPartialClose,
        onReject
    )
}