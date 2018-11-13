package com.jforex.kforexutils.order.extension

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.order.event.handler.data.SetSLEventData
import com.jforex.kforexutils.order.params.builders.OrderSLParamsBuilder
import com.jforex.kforexutils.settings.TradingSettings

fun IOrder.setSL(price: Double, block: OrderSLParamsBuilder.() -> Unit = {}) {
    val params = OrderSLParamsBuilder(price, block)
    val retryCall = { setSL(price, block) }
    runTask(
        orderCall =
        {
            setStopLossPrice(
                params.price,
                params.offerSide,
                params.trailingStep
            )
        },
        data = SetSLEventData(params.actions, retryCall)
    )
}

fun IOrder.removeSL(block: OrderSLParamsBuilder.() -> Unit = {}) = setSL(TradingSettings.noSLPrice, block)