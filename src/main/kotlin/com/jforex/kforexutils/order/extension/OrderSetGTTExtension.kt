package com.jforex.kforexutils.order.extension

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.order.changeOrder
import com.jforex.kforexutils.order.task.builders.OrderGTTParamsBuilder

fun IOrder.setGTT(
    gtt: Long,
    block: OrderGTTParamsBuilder.() -> Unit = {}
) = changeOrder(
    order = this,
    changeCall = { goodTillTime = gtt },
    taskParams = OrderGTTParamsBuilder(block)
)