package com.jforex.kforexutils.order.extension

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.order.event.consumer.data.SetTPEventConsumerData
import com.jforex.kforexutils.order.params.builders.OrderTPParamsBuilder

@Synchronized
fun IOrder.setTP(price: Double, block: OrderTPParamsBuilder.() -> Unit)
{
    val params = OrderTPParamsBuilder(price, block)
    runTask(
        orderCall = { takeProfitPrice = params.price },
        consumerData = SetTPEventConsumerData(params.tpActions)
    )
}