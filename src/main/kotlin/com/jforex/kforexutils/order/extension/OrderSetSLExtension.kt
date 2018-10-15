package com.jforex.kforexutils.order.extension

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.order.event.consumer.data.SetSLEventConsumerData
import com.jforex.kforexutils.order.params.builders.OrderSLParamsBuilder

@Synchronized
fun IOrder.setSL(price: Double, block: OrderSLParamsBuilder.() -> Unit)
{
    val params = OrderSLParamsBuilder(price, block)
    runTask(
        orderCall = {
            setStopLossPrice(
                params.price,
                params.offerSide,
                params.trailingStep
            )
        },
        consumerData = SetSLEventConsumerData(params.slActions)
    )
}