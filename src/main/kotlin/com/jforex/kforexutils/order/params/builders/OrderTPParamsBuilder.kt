package com.jforex.kforexutils.order.params.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.misc.emptyOrderCallParams
import com.jforex.kforexutils.misc.emptyOrderEventHandlers
import com.jforex.kforexutils.order.event.OrderTaskEventParams
import com.jforex.kforexutils.order.event.handler.data.SetTPEventData
import com.jforex.kforexutils.order.params.OrderTPParams
import com.jforex.kforexutils.order.params.actions.OrderTaskParams
import com.jforex.kforexutils.order.params.actions.builders.OrderTaskTPEventHandlerBuilder

@OrderDsl
class OrderTPParamsBuilder(private val tpPrice: Double) {
    var eventHandlers = emptyOrderEventHandlers
    var callParams = emptyOrderCallParams

    fun eventHandlers(block: OrderTaskTPEventHandlerBuilder.() -> Unit) {
        eventHandlers = OrderTaskTPEventHandlerBuilder(block)
    }

    fun callParams(block: OrderTaskUserParamsBuilder.() -> Unit) {
        callParams = OrderTaskUserParamsBuilder(block)
    }

    private fun build() = OrderTPParams(
        tpPrice = tpPrice,
        taskParams = OrderTaskParams(
            callParams = callParams,
            eventParams = OrderTaskEventParams(
                eventData = SetTPEventData(),
                eventHandlers = eventHandlers
            )
        )
    )

    companion object {
        operator fun invoke(price: Double, block: OrderTPParamsBuilder.() -> Unit) =
            OrderTPParamsBuilder(price)
                .apply(block)
                .build()
    }
}