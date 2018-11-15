package com.jforex.kforexutils.order.event

import arrow.data.ReaderApi
import arrow.data.map
import com.jforex.kforexutils.misc.KRunnable
import com.jforex.kforexutils.order.task.OrderTaskEventParams
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy

internal fun subscribeToOrderEvents(
    params: OrderTaskEventParams,
    onComplete: KRunnable
) = ReaderApi
    .ask<Observable<OrderEvent>>()
    .map { orderEvents ->
        val eventData = params.eventData
        val eventHandlers = params.eventHandlers
        orderEvents
            .filter { it.type in eventHandlers }
            .takeUntil { it.type in eventData.finishEventTypes }
            .subscribeBy(
                onNext = { eventHandlers.getValue(it.type)(it) },
                onComplete = { onComplete() })
    }