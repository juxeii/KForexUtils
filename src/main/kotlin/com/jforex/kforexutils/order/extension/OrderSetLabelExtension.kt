package com.jforex.kforexutils.order.extension

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.order.event.handler.data.SetLabelEventData
import com.jforex.kforexutils.order.params.builders.OrderLabelParamsBuilder

fun IOrder.setLabel(label: String, block: OrderLabelParamsBuilder.() -> Unit = {}) {
    val params = OrderLabelParamsBuilder(label, block)
    val retryCall = { setLabel(label, block) }
    runTask(
        orderCall = { setLabel(params.label) },
        data = SetLabelEventData(params.actions, retryCall)
    )
}