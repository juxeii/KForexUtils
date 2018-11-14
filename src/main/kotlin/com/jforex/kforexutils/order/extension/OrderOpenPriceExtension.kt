package com.jforex.kforexutils.order.extension

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.order.changeOrder
import com.jforex.kforexutils.order.task.builders.OrderOpenPriceParamsBuilder
import com.jforex.kforexutils.settings.TradingSettings

fun IOrder.setOpenPrice(
    openPrice: Double,
    slippage: Double = TradingSettings.defaultOpenPriceSlippage,
    block: OrderOpenPriceParamsBuilder.() -> Unit = {}
) = changeOrder(
    order = this,
    changeCall = { setOpenPrice(openPrice, slippage) },
    taskParams = OrderOpenPriceParamsBuilder(block)
)