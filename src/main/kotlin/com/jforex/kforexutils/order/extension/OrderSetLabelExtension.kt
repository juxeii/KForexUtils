package com.jforex.kforexutils.order.extension

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.order.changeToCallableCall
import com.jforex.kforexutils.order.task.builders.LabelEventParamsBuilder
import com.jforex.kforexutils.order.task.builders.OrderParamsBuilder
import com.jforex.kforexutils.order.task.runOrderTask

fun IOrder.setLabel(
    label: String,
    block: OrderParamsBuilder<LabelEventParamsBuilder>.() -> Unit = {}
)
{
    runOrderTask(
        orderCallable = changeToCallableCall(this) { setLabel(label) },
        taskParams = OrderParamsBuilder(LabelEventParamsBuilder(), block)
    ).run(kForexUtils)
}