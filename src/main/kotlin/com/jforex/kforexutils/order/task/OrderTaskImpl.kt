package com.jforex.kforexutils.order.task

import com.jforex.kforexutils.misc.KRunnable
import com.jforex.kforexutils.order.event.consumer.OrderEventHandler
import com.jforex.kforexutils.order.event.consumer.data.OrderEventConsumerData
import com.jforex.kforexutils.order.message.OrderMessageHandler
import com.jforex.kforexutils.thread.StrategyThread
import io.reactivex.rxkotlin.subscribeBy

class OrderTaskImpl(private val strategyThread: StrategyThread) : OrderTask {
    override fun run(
        orderCall: KRunnable,
        consumerData: OrderEventConsumerData,
        messageHandler: OrderMessageHandler
    ) {
        strategyThread
            .observeRunnable(orderCall)
            .subscribeBy(
                onComplete = { registerEventConsumer(messageHandler, consumerData) },
                onError = { consumerData.basicActions.onError(it) }
            )
    }

    private fun registerEventConsumer(messageHandler: OrderMessageHandler, consumerData: OrderEventConsumerData) {
        val consumer = OrderEventHandler(consumerData)
        messageHandler.registerConsumer(consumer)
    }
}