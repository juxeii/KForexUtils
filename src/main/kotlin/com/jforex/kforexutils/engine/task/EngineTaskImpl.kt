package com.jforex.kforexutils.engine.task

import com.jforex.kforexutils.misc.KCallable
import com.jforex.kforexutils.order.Order
import com.jforex.kforexutils.order.event.consumer.OrderEventConsumer
import com.jforex.kforexutils.order.event.consumer.data.OrderEventConsumerData
import com.jforex.kforexutils.order.message.OrderMessageHandlerStore
import com.jforex.kforexutils.order.params.actions.OrderBasicActions
import com.jforex.kforexutils.thread.StrategyThread
import io.reactivex.rxkotlin.subscribeBy

class EngineTaskImpl(
    private val strategyThread: StrategyThread,
    private val messageHandlerStore: OrderMessageHandlerStore
) : EngineTask {

    override fun executeOnStrategyThread(
        engineCall: KCallable<Order>,
        consumerData: OrderEventConsumerData,
        basicActions: OrderBasicActions
    ) {
        strategyThread
            .observeCallable(engineCall)
            .subscribeBy(
                onSuccess = { onComplete(it, consumerData) },
                onError = { basicActions.onError(it) }
            )
    }

    private fun onComplete(order: Order, consumerData: OrderEventConsumerData) {
        val messageHandler = messageHandlerStore.create(order)
        messageHandler.registerConsumer(OrderEventConsumer(consumerData))
    }
}