package com.jforex.kforexutils.order.event

import arrow.data.Reader
import arrow.data.ReaderApi
import arrow.data.map
import com.jforex.kforexutils.misc.KRunnable
import com.jforex.kforexutils.order.event.handler.data.OrderEventHandlerData
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy

internal fun subscribeToOrderEvents(
    handlerData: OrderEventHandlerData,
    onComplete: KRunnable = {}
): Reader<Observable<OrderEvent>, Disposable> =
    ReaderApi
        .ask<Observable<OrderEvent>>()
        .map { orderEvents ->
            with(handlerData) {
                orderEvents
                    .filter { it.type in eventHandlers }
                    .takeUntil { it.type in finishEventTypes }
                    .subscribeBy(
                        onNext =
                        {
                            if (it.type == rejectEventType) onRejectEvent(it)
                            else eventHandlers.getValue(it.type)(it)
                        },
                        onComplete = { onComplete() })
            }
        }