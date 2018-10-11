package com.jforex.kforexutils.order.extension

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.misc.executeOnStrategyThread
import com.jforex.kforexutils.order.event.consumer.data.SetSLEventConsumerData
import com.jforex.kforexutils.order.params.OrderSLParams
import com.jforex.kforexutils.order.params.builders.OrderSLParamsBuilder
import io.reactivex.rxkotlin.subscribeBy

fun IOrder.setSL(price: Double, block: OrderSLParamsBuilder.() -> Unit) {
    val slParams = OrderSLParamsBuilder(price, block)
    executeOnStrategyThread { getSLCall(this, slParams) }
        .subscribeBy(
            onComplete = { registerEventConsumer(SetSLEventConsumerData(slParams.slActions)) },
            onError = { slParams.slActions.basicActions.onError(it) })
}

private fun getSLCall(order: IOrder, slParams: OrderSLParams) = { ->
    order.setStopLossPrice(
        slParams.price,
        slParams.offerSide,
        slParams.trailingStep
    )
}