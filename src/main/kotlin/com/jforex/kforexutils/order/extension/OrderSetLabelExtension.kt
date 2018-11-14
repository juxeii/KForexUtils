package com.jforex.kforexutils.order.extension

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.order.changeOrder
import com.jforex.kforexutils.order.task.builders.OrderLabelParamsBuilder

fun IOrder.setLabel(
    label: String,
    block: OrderLabelParamsBuilder.() -> Unit = {}
) = changeOrder(
    order = this,
    changeCall = { setLabel(label) },
    taskParams = OrderLabelParamsBuilder(block)
)