package com.jforex.kforexutils.order.extension

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.order.event.handler.data.SetGTTEventData
import com.jforex.kforexutils.order.params.builders.OrderGTTParamsBuilder

fun IOrder.setGTT(gtt: Long, block: OrderGTTParamsBuilder.() -> Unit = {}) {
    val params = OrderGTTParamsBuilder(gtt, block)
    val retryCall = { setGTT(gtt, block) }
    runTask(
        orderCall = { goodTillTime = params.gtt },
        data = SetGTTEventData(params.actions, retryCall)
    )
}