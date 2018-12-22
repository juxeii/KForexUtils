package com.jforex.kforexutils.order.extension

import arrow.data.runId
import com.dukascopy.api.IOrder
import com.jforex.kforexutils.order.changeToCallableCall
import com.jforex.kforexutils.order.event.handler.data.CloseEventData
import com.jforex.kforexutils.order.task.OrderTaskParams
import com.jforex.kforexutils.order.task.builders.OrderCallHandlerBuilder
import com.jforex.kforexutils.order.task.runOrderTask
import com.jforex.kforexutils.settings.TradingSettings

@Suppress("UNCHECKED_CAST")
fun IOrder.close(
    amount: Double = 0.0,
    price: Double = 0.0,
    slippage: Double = TradingSettings.defaultCloseSlippage,
    block: OrderCallHandlerBuilder.() -> Unit = {}
) = runOrderTask(
    orderCallable = changeToCallableCall(this) { close(amount, price, slippage) },
    taskParams = OrderTaskParams(OrderCallHandlerBuilder(block), CloseEventData())
).runId(kForexUtils)
