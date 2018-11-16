package com.jforex.kforexutils.order.extension

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.order.changeOrder
import com.jforex.kforexutils.order.task.builders.OrderParamsBuilder
import com.jforex.kforexutils.order.task.builders.TPEventParamsBuilder
import com.jforex.kforexutils.settings.TradingSettings

fun IOrder.setTP(
    tpPrice: Double,
    block: OrderParamsBuilder<TPEventParamsBuilder>.() -> Unit = {}
) = changeOrder(
    order = this,
    changeCall = { takeProfitPrice = tpPrice },
    taskParams = OrderParamsBuilder(TPEventParamsBuilder(), block)
)

fun IOrder.removeTP(block: OrderParamsBuilder<TPEventParamsBuilder>.() -> Unit = {}) =
    setTP(TradingSettings.noTPPrice, block)