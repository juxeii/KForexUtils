package com.jforex.kforexutils.order.task

import com.jforex.kforexutils.misc.KRunnable
import com.jforex.kforexutils.order.Order
import com.jforex.kforexutils.order.event.consumer.OrderEventConsumer
import com.jforex.kforexutils.order.event.consumer.data.OrderEventConsumerData
import com.jforex.kforexutils.order.message.OrderMessageHandler
import com.jforex.kforexutils.order.params.actions.OrderBasicActions
import com.jforex.kforexutils.thread.StrategyThread
import io.reactivex.rxkotlin.subscribeBy

class OrderTaskBaseImpl(private val strategyThread: StrategyThread) : OrderTaskBase
{
    override fun executeOnStrategyThread(
        orderCall: KRunnable,
        order: Order,
        consumerData: OrderEventConsumerData,
        basicActions: OrderBasicActions
    )
    {
        strategyThread
            .observeRunnable(orderCall)
            .subscribeBy(
                onComplete = { registerEventConsumer(order.messageHandler, consumerData) },
                onError = { basicActions.onError(it) }
            )
    }

    private fun registerEventConsumer(messageHandler: OrderMessageHandler, consumerData: OrderEventConsumerData)
    {
        val consumer = OrderEventConsumer(consumerData)
        messageHandler.registerConsumer(consumer)
    }
}