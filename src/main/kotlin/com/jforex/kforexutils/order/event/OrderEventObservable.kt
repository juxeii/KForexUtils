package com.jforex.kforexutils.order.event

import com.jforex.kforexutils.misc.KRunnable
import com.jforex.kforexutils.order.event.handler.data.OrderEventHandlerData
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy

class OrderEventObservable(private val orderEvents: Observable<OrderEvent>)
{
    fun subscribe(
        handlerData: OrderEventHandlerData,
        onComplete: KRunnable = {}
    ) =
        with(handlerData) {
            orderEvents
                .filter { it.type in eventHandlers }
                .takeUntil { it.type in finishEventTypes }
                .subscribeBy(onNext = {
                    if (it.type == rejectEventType) taskActions.taskRetry?.onRejectEvent(it, retryCall)
                    else eventHandlers.getValue(it.type)(it)
                },
                    onComplete = { onComplete() })
        }
}