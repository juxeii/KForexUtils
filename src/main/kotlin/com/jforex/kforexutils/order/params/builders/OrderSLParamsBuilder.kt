package com.jforex.kforexutils.order.params.builders

import com.dukascopy.api.OfferSide
import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.misc.emptyOrderCallParams
import com.jforex.kforexutils.misc.emptyOrderEventHandlers
import com.jforex.kforexutils.order.event.OrderTaskEventParams
import com.jforex.kforexutils.order.event.handler.data.SetSLEventData
import com.jforex.kforexutils.order.params.OrderSLParams
import com.jforex.kforexutils.order.params.actions.OrderTaskParams
import com.jforex.kforexutils.order.params.actions.builders.OrderTaskSLEventHandlerBuilder
import com.jforex.kforexutils.settings.TradingSettings


@OrderDsl
class OrderSLParamsBuilder(private val slPrice: Double) {
    var offerSide = OfferSide.BID
    var trailingStep = TradingSettings.noTrailingStep
    var eventHandlers = emptyOrderEventHandlers
    var callParams = emptyOrderCallParams

    fun eventHandlers(block: OrderTaskSLEventHandlerBuilder.() -> Unit) {
        eventHandlers = OrderTaskSLEventHandlerBuilder(block)
    }

    fun callParams(block: OrderTaskUserParamsBuilder.() -> Unit) {
        callParams = OrderTaskUserParamsBuilder(block)
    }

    private fun build() = OrderSLParams(
        slPrice = slPrice,
        taskParams = OrderTaskParams(
            callParams = callParams,
            eventParams = OrderTaskEventParams(
                eventData = SetSLEventData(),
                eventHandlers = eventHandlers
            )
        )
    )

    companion object {
        operator fun invoke(slPrice: Double, block: OrderSLParamsBuilder.() -> Unit) =
            OrderSLParamsBuilder(slPrice)
                .apply(block)
                .build()
    }
}
