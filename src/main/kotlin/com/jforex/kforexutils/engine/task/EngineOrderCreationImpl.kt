package com.jforex.kforexutils.engine.task

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.misc.KCallable
import com.jforex.kforexutils.order.OrderStore
import com.jforex.kforexutils.order.event.consumer.OrderEventConsumer
import com.jforex.kforexutils.order.event.consumer.data.OrderEventConsumerData
import com.jforex.kforexutils.order.extension.messageHander
import com.jforex.kforexutils.order.message.OrderMessageGateway
import com.jforex.kforexutils.order.message.OrderMessageHandler
import com.jforex.kforexutils.order.params.actions.OrderBasicActions
import com.jforex.kforexutils.thread.StrategyThread
import io.reactivex.rxkotlin.subscribeBy

class EngineOrderCreationImpl(
    private val strategyThread: StrategyThread,
    private val orderStore: OrderStore,
    private val orderMessageGateway: OrderMessageGateway
) : EngineOrderCreation
{

    override fun createOnStrategyThread(
        engineCall: KCallable<IOrder>,
        consumerData: OrderEventConsumerData,
        basicActions: OrderBasicActions
    )
    {
        strategyThread
            .observeCallable(engineCall)
            .subscribeBy(
                onSuccess = { onComplete(it, consumerData) },
                onError = { basicActions.onError(it) }
            )
    }

    private fun onComplete(order: IOrder, consumerData: OrderEventConsumerData)
    {
        orderStore.add(order)
        val orderMessages = orderMessageGateway
            .observable
            .filter { it.order == order }
        order.messageHander = OrderMessageHandler(orderMessages)
        order.messageHander.registerConsumer(OrderEventConsumer(consumerData))
    }
}