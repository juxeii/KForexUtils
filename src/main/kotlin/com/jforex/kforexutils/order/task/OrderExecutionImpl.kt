package com.jforex.kforexutils.order.task

import com.jforex.kforexutils.misc.KRunnable
import com.jforex.kforexutils.order.Order
import com.jforex.kforexutils.order.event.consumer.OrderEventConsumer
import com.jforex.kforexutils.order.event.consumer.data.OrderEventConsumerData
import com.jforex.kforexutils.order.message.OrderMessageHandler
import com.jforex.kforexutils.order.message.OrderMessageHandlerStore
import com.jforex.kforexutils.order.params.actions.OrderBasicActions
import com.jforex.kforexutils.thread.StrategyThread
import io.reactivex.Single
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.zipWith

class OrderExecutionImpl(
    private val strategyThread: StrategyThread,
    private val messageHandlerStore: OrderMessageHandlerStore
) : OrderExecution {
    override fun executeOnStrategyThread(
        orderCall: KRunnable,
        order: Order,
        consumerData: OrderEventConsumerData,
        basicActions: OrderBasicActions
    ) {
        val runner = strategyThread
            .observeRunnable(orderCall)
            .toSingleDefault(true)

        Single
            .just(messageHandlerStore)
            .map {
                messageHandlerStore
                    .get(order)
                    .orElseThrow { -> NoSuchElementException("Message handler for ${order.jfOrder.label} not found!") }
            }
            .zipWith(runner) { handler, _ -> handler }
            .subscribeBy(
                onSuccess = { registerEventConsumer(it, consumerData) },
                onError = { basicActions.onError(it) }
            )
    }

    private fun registerEventConsumer(messageHandler: OrderMessageHandler, consumerData: OrderEventConsumerData) {
        val consumer = OrderEventConsumer(consumerData)
        messageHandler.registerConsumer(consumer)
    }
}