package com.jforex.kforexutils.order.extension

import arrow.core.value
import com.dukascopy.api.IOrder
import com.jforex.kforexutils.order.changeToCallableCall
import com.jforex.kforexutils.order.task.builders.OrderParamsBuilder
import com.jforex.kforexutils.order.task.builders.TPEventParamsBuilder
import com.jforex.kforexutils.order.task.runOrderTask
import com.jforex.kforexutils.settings.TradingSettings


fun IOrder.setTP(
    tpPrice: Double,
    block: OrderParamsBuilder<TPEventParamsBuilder>.() -> Unit = {}
) = runOrderTask(
    orderCallable = changeToCallableCall(this) { takeProfitPrice = tpPrice },
    taskParams = OrderParamsBuilder(TPEventParamsBuilder(), block)
).run(kForexUtils).value()

fun IOrder.removeTP(block: OrderParamsBuilder<TPEventParamsBuilder>.() -> Unit = {}) =
    setTP(TradingSettings.noTPPrice, block)