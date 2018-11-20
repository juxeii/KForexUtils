package com.jforex.kforexutils.order.event.handler

import com.jakewharton.rxrelay2.PublishRelay
import com.jforex.kforexutils.order.event.IOrderEvent
import io.reactivex.Observable

data class OrderEventHandlerObservables(
    val orderEvents: Observable<IOrderEvent>,
    val completionTriggers: PublishRelay<Unit>,
    val eventRelays: PublishRelay<PublishRelay<IOrderEvent>>
)