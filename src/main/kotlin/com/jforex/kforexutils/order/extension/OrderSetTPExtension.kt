package com.jforex.kforexutils.order.extension

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.order.params.builders.OrderTPParamsBuilder
import com.jforex.kforexutils.settings.TradingSettings

fun IOrder.setTP(price: Double, block: OrderTPParamsBuilder.() -> Unit = {}) {
    val params = OrderTPParamsBuilder(price, block)
    runTask(
        orderCall = { takeProfitPrice = params.tpPrice },
        taskParams = params.taskParams
    )
}

fun IOrder.removeTP(block: OrderTPParamsBuilder.() -> Unit = {}) = setTP(TradingSettings.noTPPrice, block)