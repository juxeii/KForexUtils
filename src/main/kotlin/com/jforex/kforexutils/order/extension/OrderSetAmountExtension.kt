package com.jforex.kforexutils.order.extension

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.order.task.builders.OrderAmountParamsBuilder

fun IOrder.setAmount(
    amount: Double,
    block: OrderAmountParamsBuilder.() -> Unit = {}
) = runTask(
    orderCall = { requestedAmount = amount },
    taskParams = OrderAmountParamsBuilder(block)
)