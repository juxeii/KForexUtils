package com.jforex.kforexutils.engine.task

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.misc.KCallable
import com.jforex.kforexutils.order.event.consumer.data.OrderEventConsumerData
import com.jforex.kforexutils.order.extension.messageHandler
import com.jforex.kforexutils.order.extension.strategyThread
import com.jforex.kforexutils.order.message.OrderMessageGateway
import com.jforex.kforexutils.order.message.OrderMessageHandler
import com.jforex.kforexutils.thread.StrategyThread
import io.reactivex.rxkotlin.subscribeBy

class EngineOrderCreationImpl(
    private val strategyThread: StrategyThread,
    private val orderMessageGateway: OrderMessageGateway
) : EngineOrderCreation {

    override fun run(
        engineCall: KCallable<IOrder>,
        consumerData: OrderEventConsumerData
    ) {
        strategyThread
            .observeCallable(engineCall)
            .subscribeBy(
                onSuccess = { onComplete(it, consumerData) },
                onError = { consumerData.basicActions.onError(it) }
            )
    }

    private fun onComplete(order: IOrder, consumerData: OrderEventConsumerData) {
        order.strategyThread = strategyThread
        order.messageHandler = OrderMessageHandler(order, orderMessageGateway)
        order.messageHandler.registerConsumer(consumerData)
    }
}