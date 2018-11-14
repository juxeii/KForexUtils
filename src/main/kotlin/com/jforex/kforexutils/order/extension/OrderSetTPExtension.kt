package com.jforex.kforexutils.order.extension

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.order.changeOrder
import com.jforex.kforexutils.order.task.builders.OrderTPParamsBuilder
import com.jforex.kforexutils.settings.TradingSettings

fun IOrder.setTP(
    tpPrice: Double,
    block: OrderTPParamsBuilder.() -> Unit = {}
) = changeOrder(
    order = this,
    orderCall = { takeProfitPrice = tpPrice },
    taskParams = OrderTPParamsBuilder(block)
)

fun IOrder.removeTP(block: OrderTPParamsBuilder.() -> Unit = {}) = setTP(TradingSettings.noTPPrice, block)