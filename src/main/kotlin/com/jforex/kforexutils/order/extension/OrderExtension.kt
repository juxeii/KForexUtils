package com.jforex.kforexutils.order.extension

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.misc.FieldProperty
import com.jforex.kforexutils.misc.KRunnable
import com.jforex.kforexutils.order.event.consumer.data.OrderEventConsumerData
import com.jforex.kforexutils.order.message.OrderMessageHandler
import com.jforex.kforexutils.thread.StrategyThread
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.launch

internal var IOrder.strategyThread: StrategyThread by FieldProperty<IOrder, StrategyThread>()
internal var IOrder.messageHandler: OrderMessageHandler by FieldProperty<IOrder, OrderMessageHandler>()

internal fun IOrder.runTask(
    orderCall: KRunnable,
    consumerData: OrderEventConsumerData
)
{
    GlobalScope.launch {
        val test = strategyThread.execute(orderCall)
        try
        {
            test.get()
            messageHandler.registerConsumer(consumerData)
        } catch (e: Exception)
        {
            consumerData.basicActions.onError(e)
        }
    }

/*
    strategyThread
        .observeRunnable(orderCall)
        .subscribeBy(
            onComplete = { messageHandler.registerConsumer(consumerData) },
            onError = { consumerData.basicActions.onError(it) }
        )*/
}

fun IOrder.isOpened() = state == IOrder.State.OPENED
fun IOrder.isFilled() = state == IOrder.State.FILLED
fun IOrder.isClosed() = state == IOrder.State.CLOSED
fun IOrder.isCanceled() = state == IOrder.State.CANCELED
fun IOrder.isConditional() = orderCommand.isConditional