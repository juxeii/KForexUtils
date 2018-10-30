package com.jforex.kforexutils.order.event.handler

import com.jforex.kforexutils.misc.KRunnable
import com.jforex.kforexutils.order.event.OrderEvent
import com.jforex.kforexutils.order.event.handler.data.OrderEventHandlerData
import com.jforex.kforexutils.rx.ObservableQueue
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class OrderEventHandler(
    private val orderEvents: Observable<OrderEvent>,
    private val handlerQueue: ObservableQueue<OrderEvent>
) {
    fun register(
        handlerData: OrderEventHandlerData,
        retryCall: KRunnable
    ) {
        val observable = createObservable(handlerData)
        val observer = createObserver(handlerData, retryCall)
        if (handlerData.type == OrderEventHandlerType.CHANGE) handlerQueue.add(observable, observer)
        else observable.subscribe(observer)
    }

    private fun createObservable(handlerData: OrderEventHandlerData) =
        with(handlerData) {
            orderEvents
                .filter { it.type in eventHandlers }
                .takeUntil { it.type in finishEventTypes }
        }

    private fun createObserver(
        handlerData: OrderEventHandlerData,
        retryCall: KRunnable
    ) = object : Observer<OrderEvent> {
        override fun onComplete() {}
        override fun onSubscribe(d: Disposable) {}
        override fun onError(e: Throwable) {}
        override fun onNext(t: OrderEvent) {
            with(handlerData) {
                if (t.type == rejectEventType) taskActions.taskRetry?.onRejectEvent(t, retryCall)
                else eventHandlers.getValue(t.type)(t)
            }
        }
    }
}