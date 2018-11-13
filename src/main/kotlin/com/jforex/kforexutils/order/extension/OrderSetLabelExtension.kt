package com.jforex.kforexutils.order.extension

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.order.task.builders.OrderLabelParamsBuilder

fun IOrder.setLabel(
    label: String,
    block: OrderLabelParamsBuilder.() -> Unit = {}
) = runTask(
    orderCall = { setLabel(label) },
    taskParams = OrderLabelParamsBuilder(block)
)