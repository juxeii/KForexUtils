package com.jforex.kforexutils.order.params.actions.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.misc.OrderEventConsumer
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.order.params.actions.OrderBasicActions
import com.jforex.kforexutils.order.params.actions.OrderGTTActions

@OrderDsl
class OrderGTTActionsBuilder {
    var basicActions: OrderBasicActions = OrderBasicActions()
    var onGTTChange: OrderEventConsumer = emptyOrderEventConsumer
    var onReject: OrderEventConsumer = emptyOrderEventConsumer

    fun basicActions(block: OrderBasicActionsBuilder.() -> Unit) {
        basicActions = OrderBasicActionsBuilder()
            .apply(block)
            .build()

    }

    fun build() = OrderGTTActions(
        basicActions = basicActions,
        onGTTChange = onGTTChange,
        onReject = onReject
    )

    companion object {
        operator fun invoke(block: OrderGTTActionsBuilder.() -> Unit) = OrderGTTActionsBuilder()
            .apply(block)
            .build()
    }
}