package com.jforex.kforexutils.order.extension

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.order.event.handler.data.SetAmountEventData
import com.jforex.kforexutils.order.params.builders.OrderAmountParamsBuilder

fun IOrder.setAmount(amount: Double, block: OrderAmountParamsBuilder.() -> Unit = {}) {
    val params = OrderAmountParamsBuilder(amount, block)
    val retryCall = { setAmount(amount, block) }
    runTask(
        orderCall = { requestedAmount = params.amount },
        data = SetAmountEventData(params.actions, retryCall)
    )
}