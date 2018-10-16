package com.jforex.kforexutils.engine

import com.dukascopy.api.IEngine
import com.dukascopy.api.IOrder
import com.jforex.kforexutils.misc.FieldProperty
import com.jforex.kforexutils.misc.KCallable
import com.jforex.kforexutils.order.event.handler.data.OrderEventConsumerData
import com.jforex.kforexutils.order.extension.messageHandler
import com.jforex.kforexutils.order.extension.strategyThread
import com.jforex.kforexutils.order.message.OrderEventGateway
import com.jforex.kforexutils.order.message.OrderMessageHandler
import com.jforex.kforexutils.thread.StrategyThread
import io.reactivex.rxkotlin.subscribeBy

internal var IEngine.strategyThread: StrategyThread by FieldProperty<IEngine, StrategyThread>()
internal var IEngine.orderMessageGateway: OrderEventGateway by FieldProperty<IEngine, OrderEventGateway>()

internal fun IEngine.createOrder(
    engineCall: KCallable<IOrder>,
    consumerData: OrderEventConsumerData
) {
    val engineCallWithOrderInitialization = {
        val order = engineCall()
        order.strategyThread = strategyThread
        order.messageHandler = OrderMessageHandler(order, orderMessageGateway)
        order.messageHandler.registerConsumer(consumerData)
    }

    strategyThread
        .observeCallable(engineCallWithOrderInitialization)
        .subscribeBy(onError = { consumerData.basicActions.onError(it) })
}