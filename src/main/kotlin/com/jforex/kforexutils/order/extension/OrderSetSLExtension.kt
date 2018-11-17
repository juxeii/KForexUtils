package com.jforex.kforexutils.order.extension

import com.dukascopy.api.IOrder
import com.dukascopy.api.OfferSide
import com.jforex.kforexutils.order.changeToCallableCall
import com.jforex.kforexutils.order.task.builders.OrderParamsBuilder
import com.jforex.kforexutils.order.task.builders.SLEventParamsBuilder
import com.jforex.kforexutils.order.task.runOrderTask
import com.jforex.kforexutils.settings.TradingSettings

fun IOrder.setSL(
    slPrice: Double,
    offerSide: OfferSide = OfferSide.BID,
    trailingStep: Double = TradingSettings.noTrailingStep,
    block: OrderParamsBuilder<SLEventParamsBuilder>.() -> Unit = {}
)
{
    runOrderTask(
        orderCallable = changeToCallableCall(this) {
            setStopLossPrice(
                slPrice,
                offerSide,
                trailingStep
            )
        },
        taskParams = OrderParamsBuilder(SLEventParamsBuilder(), block)
    ).run(kForexUtils)
}

fun IOrder.removeSL(block: OrderParamsBuilder<SLEventParamsBuilder>.() -> Unit = {}) = setSL(
    TradingSettings.noSLPrice, OfferSide.BID,
    TradingSettings.noTrailingStep,
    block
)