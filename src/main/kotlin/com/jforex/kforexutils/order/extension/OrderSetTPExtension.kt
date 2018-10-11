package com.jforex.kforexutils.order.extension

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.misc.executeOnStrategyThread
import com.jforex.kforexutils.order.event.consumer.data.SetTPEventConsumerData
import com.jforex.kforexutils.order.params.actions.builders.OrderTPActionsBuilder
import io.reactivex.rxkotlin.subscribeBy
import org.apache.logging.log4j.LogManager

private val logger = LogManager.getLogger()

fun IOrder.setTP(price: Double, block: OrderTPActionsBuilder.() -> Unit) {
    println("hallo")
    val tpActions = OrderTPActionsBuilder(block)
    executeOnStrategyThread { -> takeProfitPrice = price }
        .subscribeBy(
            onComplete = { registerEventConsumer(SetTPEventConsumerData(tpActions)) },
            onError = { tpActions.basicActions.onError(it) }
        )
}