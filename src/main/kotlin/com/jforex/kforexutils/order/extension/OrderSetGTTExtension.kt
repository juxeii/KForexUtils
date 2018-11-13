package com.jforex.kforexutils.order.extension

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.order.task.builders.OrderGTTParamsBuilder

fun IOrder.setGTT(
    gtt: Long,
    block: OrderGTTParamsBuilder.() -> Unit = {}
) = runTask(
    orderCall = { goodTillTime = gtt },
    taskParams = OrderGTTParamsBuilder(block)
)