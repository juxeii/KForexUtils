package com.jforex.kforexutils.order.event.handler

import arrow.data.ReaderApi
import arrow.data.map
import com.dukascopy.api.IOrder
import com.jforex.kforexutils.misc.KForexUtils
import com.jforex.kforexutils.misc.KRunnable
import com.jforex.kforexutils.order.event.OrderEvent
import com.jforex.kforexutils.order.event.subscribeToOrderEvents
import com.jforex.kforexutils.order.task.OrderTaskEventParams
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.zipWith

internal fun registerHandler(
    order: IOrder,
    eventParams: OrderTaskEventParams
) = ReaderApi
    .ask<KForexUtils>()
    .map { kForexUtils ->
        with(kForexUtils.handlerObservables) {
            val executionData = OrderEventExecutionData(order, eventParams)
            if (eventParams.eventData.handlerType == OrderEventHandlerType.CHANGE)
                changeEventHandlers.accept(executionData)
            else subscribeToEvents(orderEvents, executionData) {}
        }
    }

internal fun subscribeToCompletionAndHandlers(handlerObservables: OrderEventHandlerObservables) =
    with(handlerObservables) {
        completionTriggers
            .zipWith(changeEventHandlers)
            .map { it.second }
            .subscribeBy(onNext = { subscribeToEvents(orderEvents, it) { completionTriggers.accept(Unit) } })

        completionTriggers.accept(Unit)
    }

private fun subscribeToEvents(
    orderEvents: Observable<OrderEvent>,
    executionData: OrderEventExecutionData,
    completionCall: KRunnable
) {
    subscribeToOrderEvents(executionData.eventParams) { completionCall() }.run(
        orderEvents.filter
    { it.order == executionData.order })
}
