package com.jforex.kforexutils.order.extension

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.order.event.consumer.data.SetSLEventConsumerData
import com.jforex.kforexutils.order.params.OrderSLParams
import com.jforex.kforexutils.order.params.builders.OrderSLParamsBuilder

@Synchronized
fun IOrder.setSL(price: Double, block: OrderSLParamsBuilder.() -> Unit) {
    val params = OrderSLParamsBuilder(price, block)
    task.run(
        orderCall = createSLCall(this, params),
        consumerData = SetSLEventConsumerData(params.slActions),
        messageHandler = messageHandler
    )
}

private fun createSLCall(order: IOrder, params: OrderSLParams) = { ->
    order.setStopLossPrice(
        params.price,
        params.offerSide,
        params.trailingStep
    )
}