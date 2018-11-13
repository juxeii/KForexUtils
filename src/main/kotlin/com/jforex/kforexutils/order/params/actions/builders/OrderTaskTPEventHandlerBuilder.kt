package com.jforex.kforexutils.order.params.actions.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.misc.OrderEventConsumer
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.order.event.OrderEventType

@OrderDsl
class OrderTaskTPEventHandlerBuilder() {
    var onTPChange: OrderEventConsumer = emptyOrderEventConsumer
    var onTPReject: OrderEventConsumer = emptyOrderEventConsumer

    private fun build() = mapOf(
        OrderEventType.CHANGED_TP to onTPChange,
        OrderEventType.CHANGE_REJECTED to onTPReject
    )

    companion object {
        operator fun invoke(block: OrderTaskTPEventHandlerBuilder.() -> Unit) =
            OrderTaskTPEventHandlerBuilder()
                .apply(block)
                .build()
    }
}