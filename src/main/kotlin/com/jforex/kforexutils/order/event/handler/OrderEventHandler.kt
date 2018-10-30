package com.jforex.kforexutils.order.event.handler

import com.jforex.kforexutils.misc.KRunnable
import com.jforex.kforexutils.order.event.OrderEvent
import com.jforex.kforexutils.order.event.OrderEventObservableFactory
import com.jforex.kforexutils.order.event.handler.data.OrderEventHandlerData
import com.jforex.kforexutils.rx.ObservableQueue

class OrderEventHandler(
    private val observableFactory: OrderEventObservableFactory,
    private val handlerQueue: ObservableQueue<OrderEvent>
) {
    fun register(
        handlerData: OrderEventHandlerData,
        retryCall: KRunnable
    ) {
        val observable = observableFactory.createObservable(handlerData)
        val observer = observableFactory.createObserver(handlerData, retryCall)
        if (handlerData.type == OrderEventHandlerType.CHANGE) handlerQueue.add(observable, observer)
        else observable.subscribe(observer)
    }
}