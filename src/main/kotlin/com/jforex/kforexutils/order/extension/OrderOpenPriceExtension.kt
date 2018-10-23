package com.jforex.kforexutils.order.extension

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.order.event.handler.data.SetOpenPriceEventHandlerData
import com.jforex.kforexutils.order.params.builders.OrderOpenPriceParamsBuilder
import com.jforex.kforexutils.settings.TradingSettings

fun IOrder.setOpenPrice(openPrice: Double, block: OrderOpenPriceParamsBuilder.() -> Unit = {})
{
    val params = OrderOpenPriceParamsBuilder(openPrice, block)
    runTask(
        orderCall = { setOpenPrice(params.openPrice, TradingSettings.defaultOpenPriceSlippage) },
        handlerData = SetOpenPriceEventHandlerData(params.actions)
    )
}