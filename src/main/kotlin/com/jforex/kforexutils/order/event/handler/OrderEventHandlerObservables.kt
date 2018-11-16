package com.jforex.kforexutils.order.event.handler

import com.jakewharton.rxrelay2.PublishRelay
import com.jforex.kforexutils.order.event.OrderEvent
import com.jforex.kforexutils.order.event.OrderEventsConfigurationParams
import io.reactivex.Observable

data class OrderEventHandlerObservables(
    val orderEvents: Observable<OrderEvent>,
    val completionTriggers: PublishRelay<Unit>,
    val changeEventHandlers: PublishRelay<OrderEventsConfigurationParams>
)