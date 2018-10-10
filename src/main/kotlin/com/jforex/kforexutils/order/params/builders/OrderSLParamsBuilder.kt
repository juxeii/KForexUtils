package com.jforex.kforexutils.order.params.builders

import com.dukascopy.api.IOrder
import com.dukascopy.api.OfferSide
import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.order.params.OrderSLParams
import com.jforex.kforexutils.order.params.actions.OrderSLActions
import com.jforex.kforexutils.order.params.actions.builders.OrderSLActionsBuilder
import com.jforex.kforexutils.settings.TradingSettings

fun orderSLParams(
    order: IOrder,
    price: Double,
    block: OrderSLParamsBuilder.() -> Unit
) =
    OrderSLParamsBuilder(
        order,
        price
    )
        .apply(block)
        .build()

@OrderDsl
class OrderSLParamsBuilder(
    private val order: IOrder,
    private val price: Double
)
{
    private val offerSide: OfferSide = OfferSide.BID
    private val trailingStep: Double = TradingSettings.noTrailingStep
    private var slActions = OrderSLActions()

    fun slActions(block: OrderSLActionsBuilder.() -> Unit)
    {
        slActions = OrderSLActionsBuilder()
            .apply(block)
            .build()
    }

    fun build() = OrderSLParams(
        order = order,
        price = price,
        offerSide = offerSide,
        trailingStep = trailingStep,
        slActions = slActions
    )
}