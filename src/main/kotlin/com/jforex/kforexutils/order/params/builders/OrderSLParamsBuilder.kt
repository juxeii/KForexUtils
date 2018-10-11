package com.jforex.kforexutils.order.params.builders

import com.dukascopy.api.OfferSide
import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.order.params.OrderSLParams
import com.jforex.kforexutils.order.params.actions.OrderSLActions
import com.jforex.kforexutils.order.params.actions.builders.OrderSLActionsBuilder
import com.jforex.kforexutils.settings.TradingSettings

@OrderDsl
class OrderSLParamsBuilder(private val price: Double) {
    private val offerSide: OfferSide = OfferSide.BID
    private val trailingStep: Double = TradingSettings.noTrailingStep
    private var slActions = OrderSLActions()

    fun slActions(block: OrderSLActionsBuilder.() -> Unit) {
        slActions = OrderSLActionsBuilder()
            .apply(block)
            .build()
    }

    fun build() = OrderSLParams(
        price = price,
        offerSide = offerSide,
        trailingStep = trailingStep,
        slActions = slActions
    )

    companion object {
        operator fun invoke(price: Double, block: OrderSLParamsBuilder.() -> Unit) =
            OrderSLParamsBuilder(price)
                .apply(block)
                .build()
    }
}