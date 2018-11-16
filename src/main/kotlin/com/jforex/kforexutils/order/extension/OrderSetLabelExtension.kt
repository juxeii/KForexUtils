package com.jforex.kforexutils.order.extension

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.order.changeOrder
import com.jforex.kforexutils.order.task.builders.LabelEventParamsBuilder
import com.jforex.kforexutils.order.task.builders.OrderParamsBuilder

fun IOrder.setLabel(
    label: String,
    block: OrderParamsBuilder<LabelEventParamsBuilder>.() -> Unit = {}
) = changeOrder(
    order = this,
    changeCall = { setLabel(label) },
    taskParams = OrderParamsBuilder(LabelEventParamsBuilder(), block)
)