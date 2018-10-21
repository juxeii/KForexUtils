package com.jforex.kforexutils.order.params.builders

import com.dukascopy.api.OfferSide
import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.order.params.OrderSLParams
import com.jforex.kforexutils.order.params.actions.OrderSLActions
import com.jforex.kforexutils.order.params.actions.builders.OrderSLActionsBuilder
import com.jforex.kforexutils.settings.TradingSettings

@OrderDsl
class OrderSLParamsBuilder(private val price: Double)
{
    var offerSide = OfferSide.BID
    var trailingStep = TradingSettings.noTrailingStep
    var actions = OrderSLActions()

    fun actions(block: OrderSLActionsBuilder.() -> Unit) {
        actions = OrderSLActionsBuilder(block)
    }

    fun build() = OrderSLParams(
        price = price,
        offerSide = offerSide,
        trailingStep = trailingStep,
        actions = actions
    )

    companion object {
        operator fun invoke(price: Double, block: OrderSLParamsBuilder.() -> Unit) =
            OrderSLParamsBuilder(price)
                .apply(block)
                .build()
    }
}