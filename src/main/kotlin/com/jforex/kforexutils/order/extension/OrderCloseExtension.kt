package com.jforex.kforexutils.order.extension

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.order.changeOrder
import com.jforex.kforexutils.order.task.builders.CloseEventParamsBuilder
import com.jforex.kforexutils.order.task.builders.OrderParamsBuilder
import com.jforex.kforexutils.settings.TradingSettings

fun IOrder.close(
    amount: Double = 0.0,
    price: Double = 0.0,
    slippage: Double = TradingSettings.defaultCloseSlippage,
    block: OrderParamsBuilder<CloseEventParamsBuilder>.() -> Unit = {}
) = changeOrder(
    order = this,
    changeCall = { close(amount, price, slippage) },
    taskParams = OrderParamsBuilder(CloseEventParamsBuilder(), block)
)
