package com.jforex.kforexutils.order.extension

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.misc.KRunnable
import com.jforex.kforexutils.misc.executeOnStrategyThread
import com.jforex.kforexutils.order.event.consumer.data.CloseEventConsumerData
import com.jforex.kforexutils.order.params.builders.OrderCloseParamsBuilder
import io.reactivex.rxkotlin.subscribeBy

fun IOrder.close(block: OrderCloseParamsBuilder.() -> Unit) {
    val closeParams = OrderCloseParamsBuilder(block)
    closeParams.closeActions.basicActions
    val price = closeParams.price
    val amount = closeParams.amount
    val closeCall: KRunnable = if (price == 0.0) { -> close(amount) }
    else { ->
        close(
            amount,
            price,
            closeParams.slippage
        )
    }
    executeOnStrategyThread { closeCall }
        .subscribeBy(
            onComplete = { registerEventConsumer(CloseEventConsumerData(closeParams.closeActions)) },
            onError = { closeParams.closeActions.basicActions.onError(it) }
        )
}