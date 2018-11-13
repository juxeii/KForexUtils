package com.jforex.kforexutils.order.extension

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.order.task.builders.OrderOpenPriceParamsBuilder
import com.jforex.kforexutils.settings.TradingSettings

fun IOrder.setOpenPrice(
    openPrice: Double,
    slippage: Double = TradingSettings.defaultOpenPriceSlippage,
    block: OrderOpenPriceParamsBuilder.() -> Unit = {}
) = runTask(
    orderCall = { setOpenPrice(openPrice, slippage) },
    taskParams = OrderOpenPriceParamsBuilder(block)
)