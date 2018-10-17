package com.jforex.kforexutils.order.extension

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.misc.FieldProperty
import com.jforex.kforexutils.misc.KRunnable
import com.jforex.kforexutils.order.event.OrderEventHandler
import com.jforex.kforexutils.order.event.handler.data.OrderEventHandlerData
import com.jforex.kforexutils.thread.StrategyThread
import io.reactivex.rxkotlin.subscribeBy

internal var IOrder.strategyThread: StrategyThread by FieldProperty<IOrder, StrategyThread>()
internal var IOrder.eventHandler: OrderEventHandler by FieldProperty<IOrder, OrderEventHandler>()

internal fun IOrder.runTask(
    orderCall: KRunnable,
    consumerData: OrderEventHandlerData
) {
    val orderCallWithConsumerRegistration = {
        orderCall()
        eventHandler.registerHandler(consumerData)
    }

    strategyThread
        .observeRunnable(orderCallWithConsumerRegistration)
        .subscribeBy(onError = { consumerData.basicActions.onError(it) })
}

fun IOrder.isOpened() = state == IOrder.State.OPENED
fun IOrder.isFilled() = state == IOrder.State.FILLED
fun IOrder.isClosed() = state == IOrder.State.CLOSED
fun IOrder.isCanceled() = state == IOrder.State.CANCELED
fun IOrder.isConditional() = orderCommand.isConditional