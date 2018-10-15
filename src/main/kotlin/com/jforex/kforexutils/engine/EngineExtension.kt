package com.jforex.kforexutils.engine

import com.dukascopy.api.IEngine
import com.dukascopy.api.IOrder
import com.jforex.kforexutils.misc.FieldProperty
import com.jforex.kforexutils.misc.KCallable
import com.jforex.kforexutils.order.event.consumer.data.OrderEventConsumerData
import com.jforex.kforexutils.order.extension.messageHandler
import com.jforex.kforexutils.order.extension.strategyThread
import com.jforex.kforexutils.order.message.OrderMessageGateway
import com.jforex.kforexutils.order.message.OrderMessageHandler
import com.jforex.kforexutils.thread.StrategyThread
import io.reactivex.rxkotlin.subscribeBy

internal var IEngine.strategyThread: StrategyThread by FieldProperty<IEngine, StrategyThread>()
internal var IEngine.orderMessageGateway: OrderMessageGateway by FieldProperty<IEngine, OrderMessageGateway>()

internal fun IEngine.createOrder(
    engineCall: KCallable<IOrder>,
    consumerData: OrderEventConsumerData
)
{
    strategyThread
        .observeCallable(engineCall)
        .subscribeBy(
            onSuccess = {
                it.strategyThread = strategyThread
                it.messageHandler = OrderMessageHandler(it, orderMessageGateway)
                it.messageHandler.registerConsumer(consumerData)
            },
            onError = { consumerData.basicActions.onError(it) }
        )
}