package com.jforex.kforexutils.order.params

import com.dukascopy.api.OfferSide
import com.jforex.kforexutils.order.params.actions.OrderSLActions
import com.jforex.kforexutils.settings.TradingSettings

data class OrderSLParams(
    val price: Double,
    val offerSide: OfferSide = OfferSide.BID,
    val trailingStep: Double = TradingSettings.noTrailingStep,
    val actions: OrderSLActions = OrderSLActions()
)