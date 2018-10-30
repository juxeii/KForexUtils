package com.jforex.kforexutils.order.event

import com.jforex.kforexutils.misc.KRunnable
import com.jforex.kforexutils.order.event.handler.data.OrderEventHandlerData
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class OrderEventObservableFactory(private val orderEvents: Observable<OrderEvent>) {

    fun createObservable(handlerData: OrderEventHandlerData) =
        with(handlerData) {
            orderEvents
                .filter { it.type in eventHandlers }
                .takeUntil { it.type in finishEventTypes }
        }

    fun createObserver(
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