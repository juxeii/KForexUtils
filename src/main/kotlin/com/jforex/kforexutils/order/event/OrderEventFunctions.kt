package com.jforex.kforexutils.order.event

import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy

internal fun subscribeToOrderEvents(
    orderEvents: Observable<OrderEvent>,
    configuration: OrderEventsConfiguration
) = with(configuration) {
    orderEvents
        .filter { orderEvent -> orderEvent.order == order }
        .takeUntil { orderEvent -> orderEvent.type in finishTypes }
        .filter { orderEvent -> orderEvent.type in handlers }
        .subscribeBy(
            onNext = { handlers.getValue(it.type)(it) },
            onComplete = completionCall
        )
}