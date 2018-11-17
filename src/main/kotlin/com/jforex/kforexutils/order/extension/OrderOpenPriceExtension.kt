package com.jforex.kforexutils.order.extension

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.order.changeToCallableCall
import com.jforex.kforexutils.order.task.builders.OpenPriceEventParamsBuilder
import com.jforex.kforexutils.order.task.builders.OrderParamsBuilder
import com.jforex.kforexutils.order.task.runOrderTask
import com.jforex.kforexutils.settings.TradingSettings

fun IOrder.setOpenPrice(
    openPrice: Double,
    slippage: Double = TradingSettings.defaultOpenPriceSlippage,
    block: OrderParamsBuilder<OpenPriceEventParamsBuilder>.() -> Unit = {}
)
{
    runOrderTask(
        orderCallable = changeToCallableCall(this) { setOpenPrice(openPrice, slippage) },
        taskParams = OrderParamsBuilder(OpenPriceEventParamsBuilder(), block)
    ).run(kForexUtils)
}