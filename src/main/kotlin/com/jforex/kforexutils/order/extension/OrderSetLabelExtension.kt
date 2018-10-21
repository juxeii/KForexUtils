package com.jforex.kforexutils.order.extension

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.order.event.handler.data.SetLabelEventHandlerData
import com.jforex.kforexutils.order.params.builders.OrderLabelParamsBuilder

fun IOrder.setLabel(label: String, block: OrderLabelParamsBuilder.() -> Unit = {}) {
    val params = OrderLabelParamsBuilder(label, block)
    runTask(
        orderCall = { setLabel(params.label) },
        handlerData = SetLabelEventHandlerData(params.actions)
    )
}