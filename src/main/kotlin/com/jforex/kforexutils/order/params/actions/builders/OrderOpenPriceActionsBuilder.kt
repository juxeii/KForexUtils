package com.jforex.kforexutils.order.params.actions.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.misc.OrderEventConsumer
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.order.params.actions.OrderBasicActions
import com.jforex.kforexutils.order.params.actions.OrderOpenPriceActions

@OrderDsl
class OrderOpenPriceActionsBuilder {
    var basicActions: OrderBasicActions = OrderBasicActions()
    var onOpenPriceChange: OrderEventConsumer = emptyOrderEventConsumer
    var onReject: OrderEventConsumer = emptyOrderEventConsumer

    fun basicActions(block: OrderBasicActionsBuilder.() -> Unit) {
        basicActions = OrderBasicActionsBuilder()
            .apply(block)
            .build()

    }

    fun build() = OrderOpenPriceActions(
        basicActions = basicActions,
        onOpenPriceChange = onOpenPriceChange,
        onReject = onReject
    )

    companion object {
        operator fun invoke(block: OrderOpenPriceActionsBuilder.() -> Unit) = OrderOpenPriceActionsBuilder()
            .apply(block)
            .build()
    }
}