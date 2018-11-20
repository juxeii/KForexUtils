package com.jforex.kforexutils.order.event.handler

import com.jakewharton.rxrelay2.PublishRelay
import com.jforex.kforexutils.order.event.OrderEvent
import io.reactivex.Observable

data class OrderEventHandlerObservables(
    val orderEvents: Observable<OrderEvent>,
    val completionTriggers: PublishRelay<Unit>,
    val eventRelays: PublishRelay<OrderEvent>
)