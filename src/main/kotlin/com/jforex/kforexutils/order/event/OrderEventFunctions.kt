package com.jforex.kforexutils.order.event

import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy

internal fun subscribeToOrderEvents(
    orderEvents: Observable<OrderEvent>,
    params: OrderEventsConfigurationParams
) = with(params) {
    orderEvents
        .filter { it.order == order }
        .takeUntil { it.type in finishEventTypes }
        .filter { it.type in eventHandlers }
        .subscribeBy(
            onNext = { eventHandlers.getValue(it.type)(it) },
            onComplete = { completionCall() }
        )
}