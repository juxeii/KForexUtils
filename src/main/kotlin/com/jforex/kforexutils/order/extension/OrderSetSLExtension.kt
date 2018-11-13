package com.jforex.kforexutils.order.extension

import com.dukascopy.api.IOrder
import com.dukascopy.api.OfferSide
import com.jforex.kforexutils.order.task.builders.OrderSLParamsBuilder
import com.jforex.kforexutils.settings.TradingSettings

fun IOrder.setSL(
    slPrice: Double,
    offerSide: OfferSide = OfferSide.BID,
    trailingStep: Double = TradingSettings.noTrailingStep,
    block: OrderSLParamsBuilder.() -> Unit = {}
) = runTask(
    orderCall =
    {
        setStopLossPrice(
            slPrice,
            offerSide,
            trailingStep
        )
    },
    taskParams = OrderSLParamsBuilder(block)
)

fun IOrder.removeSL(block: OrderSLParamsBuilder.() -> Unit = {}) = setSL(
    TradingSettings.noSLPrice, OfferSide.BID,
    TradingSettings.noTrailingStep,
    block
)