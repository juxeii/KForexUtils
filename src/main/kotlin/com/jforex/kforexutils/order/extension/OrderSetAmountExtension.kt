package com.jforex.kforexutils.order.extension

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.order.changeToCallableCall
import com.jforex.kforexutils.order.task.builders.AmountEventParamsBuilder
import com.jforex.kforexutils.order.task.builders.OrderParamsBuilder
import com.jforex.kforexutils.order.task.runOrderTask

fun IOrder.setAmount(
    amount: Double,
    block: OrderParamsBuilder<AmountEventParamsBuilder>.() -> Unit = {}
)
{
    runOrderTask(
        orderCallable = changeToCallableCall(this) { requestedAmount = amount },
        taskParams = OrderParamsBuilder(AmountEventParamsBuilder(), block)
    ).run(kForexUtils)
}