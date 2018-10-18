package com.jforex.kforexutils.engine

import com.dukascopy.api.IEngine
import com.dukascopy.api.IOrder
import com.jforex.kforexutils.misc.FieldProperty
import com.jforex.kforexutils.misc.KCallable
import com.jforex.kforexutils.order.event.OrderEventGateway
import com.jforex.kforexutils.order.event.OrderEventHandler
import com.jforex.kforexutils.order.event.handler.data.OrderEventHandlerData
import com.jforex.kforexutils.order.extension.eventHandler
import com.jforex.kforexutils.order.extension.strategyThread
import com.jforex.kforexutils.thread.StrategyThread
import io.reactivex.rxkotlin.subscribeBy

internal var IEngine.strategyThread: StrategyThread by FieldProperty<IEngine, StrategyThread>()
internal var IEngine.orderMessageGateway: OrderEventGateway by FieldProperty<IEngine, OrderEventGateway>()

internal fun IEngine.createOrder(
    engineCall: KCallable<IOrder>,
    consumerData: OrderEventHandlerData
) {
    val engineCallWithOrderInitialization = {
        val order = engineCall()
        order.strategyThread = strategyThread

        val orderEvents = orderMessageGateway
            .observable
            .filter { it.order == order }
        order.eventHandler = OrderEventHandler(orderEvents)
        order.eventHandler.enqueueEventObserver(consumerData)
    }

    strategyThread
        .observeCallable(engineCallWithOrderInitialization)
        .subscribeBy(onError = { consumerData.basicActions.onError(it) })
}