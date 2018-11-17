package com.jforex.kforexutils.order.extension

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.order.changeToCallableCall
import com.jforex.kforexutils.order.task.builders.CloseEventParamsBuilder
import com.jforex.kforexutils.order.task.builders.OrderParamsBuilder
import com.jforex.kforexutils.order.task.runOrderTask
import com.jforex.kforexutils.settings.TradingSettings

fun IOrder.close(
    amount: Double = 0.0,
    price: Double = 0.0,
    slippage: Double = TradingSettings.defaultCloseSlippage,
    block: OrderParamsBuilder<CloseEventParamsBuilder>.() -> Unit = {}
)
{
    runOrderTask(
        orderCallable = changeToCallableCall(this) { close(amount, price, slippage) },
        taskParams = OrderParamsBuilder(CloseEventParamsBuilder(), block)
    ).run(kForexUtils)
}
