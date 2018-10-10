package com.jforex.kforexutils.order.params.actions.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.misc.OrderEventConsumer
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.order.params.actions.OrderBasicActions
import com.jforex.kforexutils.order.params.actions.OrderTPActions

@OrderDsl
class OrderTPActionsBuilder
{
    var basicActions: OrderBasicActions = OrderBasicActions()
    var onTPChange: OrderEventConsumer = emptyOrderEventConsumer
    var onReject: OrderEventConsumer = emptyOrderEventConsumer

    fun basicActions(block: OrderBasicActionsBuilder.() -> Unit)
    {
        basicActions = OrderBasicActionsBuilder()
            .apply(block)
            .build()

    }

    fun build() = OrderTPActions(
        basicActions = basicActions,
        onTPChange = onTPChange,
        onReject = onReject
    )
}