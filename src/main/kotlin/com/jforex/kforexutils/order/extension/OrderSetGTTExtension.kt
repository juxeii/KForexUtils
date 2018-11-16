package com.jforex.kforexutils.order.extension

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.order.changeOrder
import com.jforex.kforexutils.order.task.builders.GTTEventParamsBuilder
import com.jforex.kforexutils.order.task.builders.OrderParamsBuilder

fun IOrder.setGTT(
    gtt: Long,
    block: OrderParamsBuilder<GTTEventParamsBuilder>.() -> Unit = {}
) = changeOrder(
    order = this,
    changeCall = { goodTillTime = gtt },
    taskParams = OrderParamsBuilder(GTTEventParamsBuilder(), block)
)