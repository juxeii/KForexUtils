package com.jforex.kforexutils.order.extension

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.order.event.handler.data.SetAmountEventHandlerData
import com.jforex.kforexutils.order.params.builders.OrderAmountParamsBuilder

fun IOrder.setAmount(amount: Double, block: OrderAmountParamsBuilder.() -> Unit = {}) {
    val params = OrderAmountParamsBuilder(amount, block)
    runTask(
        orderCall = { requestedAmount = params.amount },
        consumerData = SetAmountEventHandlerData(params.actions)
    )
}