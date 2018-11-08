package com.jforex.kforexutils.order.extension

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.misc.KRunnable
import com.jforex.kforexutils.order.event.handler.data.SetAmountEventHandlerData
import com.jforex.kforexutils.order.params.builders.OrderAmountParamsBuilder

fun IOrder.setAmount(amount: Double, block: OrderAmountParamsBuilder.() -> Unit = {}) {
    val params = OrderAmountParamsBuilder(amount, block)
    runTask(
        orderCall = { requestedAmount = params.amount },
        handlerDataProvider = { retryCall: KRunnable -> SetAmountEventHandlerData(params.actions, retryCall) }
    )
}