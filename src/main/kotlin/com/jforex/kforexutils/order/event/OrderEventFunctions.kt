package com.jforex.kforexutils.order.event

import arrow.data.ReaderApi
import arrow.data.map
import com.jforex.kforexutils.misc.KForexUtils
import io.reactivex.rxkotlin.subscribeBy

internal fun subscribeToOrderEvents(configuration: OrderEventsConfiguration) = ReaderApi
    .ask<KForexUtils>()
    .map { kForexUtils ->
        with(configuration) {
            kForexUtils
                .orderEvents
                .filter { orderEvent -> orderEvent.order == order }
                .takeUntil { orderEvent -> orderEvent.type in finishTypes }
                .filter { orderEvent -> orderEvent.type in handlers }
                .subscribeBy(
                    onNext = { handlers.getValue(it.type)(it) },
                    onComplete = completionCall
                )
        }
    }