package com.jforex.kforexutils.order.extension

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.order.event.handler.data.SetGTTEventHandlerData
import com.jforex.kforexutils.order.params.builders.OrderGTTParamsBuilder

fun IOrder.setGTT(gtt: Long, block: OrderGTTParamsBuilder.() -> Unit = {}) {
    val params = OrderGTTParamsBuilder(gtt, block)
    runTask(
        orderCall = { goodTillTime = params.gtt },
        consumerData = SetGTTEventHandlerData(params.actions)
    )
}