package com.jforex.kforexutils.order.event

import arrow.data.Reader
import arrow.data.ReaderApi
import arrow.data.map
import com.jforex.kforexutils.misc.KRunnable
import com.jforex.kforexutils.order.params.actions.OrderEventExecutionParams
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy

internal fun subscribeToOrderEvents(
    params: OrderEventExecutionParams,
    onComplete: KRunnable = {}
) = ReaderApi
    .ask<Observable<OrderEvent>>()
    .map { orderEvents ->
        val eventData = params.eventParams.eventData
        val eventHandlers = params.eventParams.eventHandlers
        val retryHandler = params.retryHandler
        orderEvents
            .filter { it.type in eventHandlers }
            .takeUntil { it.type in eventData.finishEventTypes }
            .subscribeBy(
                onNext =
                {
                    if (it.type in eventData.rejectEventTypes) retryHandler.onRejectEvent(it, params.retryCall)
                    else eventHandlers.getValue(it.type)(it)
                },
                onComplete = { onComplete() })
    }