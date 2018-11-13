package com.jforex.kforexutils.order.params.actions.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.misc.OrderEventConsumer
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.order.event.OrderEventType

@OrderDsl
class OrderTaskSLEventHandlerBuilder() {
    var onSLChange: OrderEventConsumer = emptyOrderEventConsumer
    var onSLReject: OrderEventConsumer = emptyOrderEventConsumer

    private fun build() = mapOf(
        OrderEventType.CHANGED_SL to onSLChange,
        OrderEventType.CHANGE_REJECTED to onSLReject
    )

    companion object {
        operator fun invoke(block: OrderTaskSLEventHandlerBuilder.() -> Unit) =
            OrderTaskSLEventHandlerBuilder()
                .apply(block)
                .build()
    }
}