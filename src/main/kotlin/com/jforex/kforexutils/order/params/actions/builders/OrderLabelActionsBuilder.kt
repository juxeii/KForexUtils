package com.jforex.kforexutils.order.params.actions.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.misc.OrderEventConsumer
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.order.params.actions.OrderBasicActions
import com.jforex.kforexutils.order.params.actions.OrderLabelActions

@OrderDsl
class OrderLabelActionsBuilder {
    var basicActions: OrderBasicActions = OrderBasicActions()
    var onLabelChange: OrderEventConsumer = emptyOrderEventConsumer
    var onReject: OrderEventConsumer = emptyOrderEventConsumer

    fun basicActions(block: OrderBasicActionsBuilder.() -> Unit) {
        basicActions = OrderBasicActionsBuilder()
            .apply(block)
            .build()

    }

    fun build() = OrderLabelActions(
        basicActions = basicActions,
        onLabelChange = onLabelChange,
        onReject = onReject
    )

    companion object {
        operator fun invoke(block: OrderLabelActionsBuilder.() -> Unit) = OrderLabelActionsBuilder()
            .apply(block)
            .build()
    }
}