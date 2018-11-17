package com.jforex.kforexutils.order.extension

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.order.changeToCallableCall
import com.jforex.kforexutils.order.task.builders.GTTEventParamsBuilder
import com.jforex.kforexutils.order.task.builders.OrderParamsBuilder
import com.jforex.kforexutils.order.task.runOrderTask

fun IOrder.setGTT(
    gtt: Long,
    block: OrderParamsBuilder<GTTEventParamsBuilder>.() -> Unit = {}
)
{
    runOrderTask(
        orderCallable = changeToCallableCall(this) { goodTillTime = gtt },
        taskParams = OrderParamsBuilder(GTTEventParamsBuilder(), block)
    ).run(kForexUtils)
}