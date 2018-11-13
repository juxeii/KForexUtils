package com.jforex.kforexutils.order.extension

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.order.task.builders.OrderTPParamsBuilder
import com.jforex.kforexutils.settings.TradingSettings

fun IOrder.close(
    amount: Double = 0.0,
    price: Double = 0.0,
    slippage: Double = TradingSettings.defaultCloseSlippage,
    block: OrderTPParamsBuilder.() -> Unit = {}
) = runTask(
    orderCall = { close(amount, price, slippage) },
    taskParams = OrderTPParamsBuilder(block)
)
