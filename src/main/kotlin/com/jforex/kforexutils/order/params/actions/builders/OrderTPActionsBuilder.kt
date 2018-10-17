package com.jforex.kforexutils.order.params.actions.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.order.params.actions.OrderTPActions

@OrderDsl
class OrderTPActionsBuilder : OrderBasicActionsBuilderBase()
{
    var onTPChange = emptyOrderEventConsumer
    var onReject = emptyOrderEventConsumer

    fun build() = OrderTPActions(
        basicActions = basicActions,
        onTPChange = onTPChange,
        onReject = onReject
    )

    companion object
    {
        operator fun invoke(block: OrderTPActionsBuilder.() -> Unit) = OrderTPActionsBuilder()
            .apply(block)
            .build()
    }
}