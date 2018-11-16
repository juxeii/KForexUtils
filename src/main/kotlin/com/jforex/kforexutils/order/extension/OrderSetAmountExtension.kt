package com.jforex.kforexutils.order.extension

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.order.changeOrder
import com.jforex.kforexutils.order.task.builders.AmountEventParamsBuilder
import com.jforex.kforexutils.order.task.builders.OrderParamsBuilder

fun IOrder.setAmount(
    amount: Double,
    block: OrderParamsBuilder<AmountEventParamsBuilder>.() -> Unit = {}
) = changeOrder(
    order = this,
    changeCall = { requestedAmount = amount },
    taskParams = OrderParamsBuilder(AmountEventParamsBuilder(), block)
)