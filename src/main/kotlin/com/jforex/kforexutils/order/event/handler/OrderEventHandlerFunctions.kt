package com.jforex.kforexutils.order.event.handler

import com.jforex.kforexutils.misc.KRunnable
import com.jforex.kforexutils.order.event.OrderEvent
import com.jforex.kforexutils.order.event.subscribeToOrderEvents
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.zipWith

internal fun subscribeToCompletionAndHandlers(handlerObservables: OrderEventHandlerObservables) =
    with(handlerObservables) {
        completionTriggers
            .zipWith(changeEventHandlers)
            .map { it.second }
            .subscribeBy(onNext = { subscribeToEvents(orderEvents, it) { completionTriggers.accept(Unit) } })

        completionTriggers.accept(Unit)
    }


internal fun subscribeToEvents(
    orderEvents: Observable<OrderEvent>,
    executionData: OrderEventExecutionData,
    completionCall: KRunnable
) {
    subscribeToOrderEvents(executionData.params) { completionCall() }.run(orderEvents.filter
    { it.order == executionData.order })
}
