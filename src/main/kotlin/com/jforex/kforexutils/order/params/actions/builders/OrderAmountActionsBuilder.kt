package com.jforex.kforexutils.order.params.actions.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.misc.OrderEventConsumer
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.order.params.actions.OrderAmountActions
import com.jforex.kforexutils.order.params.actions.OrderBasicActions

@OrderDsl
class OrderAmountActionsBuilder {
    var basicActions: OrderBasicActions = OrderBasicActions()
    var onAmountChange: OrderEventConsumer = emptyOrderEventConsumer
    var onReject: OrderEventConsumer = emptyOrderEventConsumer

    fun basicActions(block: OrderBasicActionsBuilder.() -> Unit) {
        basicActions = OrderBasicActionsBuilder()
            .apply(block)
            .build()

    }

    fun build() = OrderAmountActions(
        basicActions = basicActions,
        onAmountChange = onAmountChange,
        onReject = onReject
    )

    companion object {
        operator fun invoke(block: OrderAmountActionsBuilder.() -> Unit) = OrderAmountActionsBuilder()
            .apply(block)
            .build()
    }
}