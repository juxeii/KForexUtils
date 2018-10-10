package com.jforex.kforexutils.order.params

import com.dukascopy.api.IOrder
import com.dukascopy.api.OfferSide
import com.jforex.kforexutils.order.params.actions.OrderSLActions
import com.jforex.kforexutils.settings.TradingSettings

data class OrderSLParams(
    val order: IOrder,
    val price: Double,
    val slActions: OrderSLActions = OrderSLActions(),
    val offerSide: OfferSide = OfferSide.BID,
    val trailingStep: Double = TradingSettings.noTrailingStep
)