package com.jforex.kforexutils.order.extension

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.order.changeOrder
import com.jforex.kforexutils.order.task.builders.OpenPriceEventParamsBuilder
import com.jforex.kforexutils.order.task.builders.OrderParamsBuilder
import com.jforex.kforexutils.settings.TradingSettings

fun IOrder.setOpenPrice(
    openPrice: Double,
    slippage: Double = TradingSettings.defaultOpenPriceSlippage,
    block: OrderParamsBuilder<OpenPriceEventParamsBuilder>.() -> Unit = {}
) = changeOrder(
    order = this,
    changeCall = { setOpenPrice(openPrice, slippage) },
    taskParams = OrderParamsBuilder(OpenPriceEventParamsBuilder(), block)
)