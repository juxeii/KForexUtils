package com.jforex.kforexutils.order.task

import arrow.core.value
import arrow.data.ReaderApi
import arrow.data.map
import com.dukascopy.api.IOrder
import com.jakewharton.rxrelay2.PublishRelay
import com.jforex.kforexutils.context.executeTaskOnStrategyThreadBlocking
import com.jforex.kforexutils.misc.KCallable
import com.jforex.kforexutils.misc.KForexUtils
import com.jforex.kforexutils.order.event.IOrderEvent
import com.jforex.kforexutils.order.event.handler.OrderEventHandlerType
import com.jforex.kforexutils.order.event.handler.data.OrderEventData
import io.reactivex.Observable
import io.reactivex.Single

internal data class TaskCallResult(
    val order: IOrder,
    val observable: Observable<IOrderEvent>
)

internal fun runOrderTask(
    orderCallable: KCallable<IOrder>,
    taskParams: OrderTaskParams
) = with(taskParams.callHandlers) {
    createStrategyCallable(taskParams.eventData, orderCallable)
        .map { callable ->
            Single
                .fromCallable(callable)
                .doOnSubscribe { onStart() }
                .doOnError { onError(it) }
                .doOnSuccess { onSuccess(it.order) }
                .flatMapObservable { it.observable }
        }
}

private fun createStrategyCallable(
    eventData: OrderEventData,
    orderCallable: KCallable<IOrder>
) = ReaderApi
    .ask<KForexUtils>()
    .map { kForexUtils ->
        {
            executeTaskOnStrategyThreadBlocking {
                val order = orderCallable()
                createBaseObservable(eventData, order)
                    .map { TaskCallResult(order, it) }
                    .run(kForexUtils)
                    .value()
            }.run(kForexUtils).value()
        }
    }

private fun createBaseObservable(
    eventData: OrderEventData,
    order: IOrder
) =
    if (eventData.handlerType != OrderEventHandlerType.CHANGE) observableForNonChange(order, eventData)
    else observableForChange(order, eventData)

private fun observableForNonChange(
    order: IOrder,
    eventData: OrderEventData
) = ReaderApi
    .ask<KForexUtils>()
    .map { configureObservable(it.orderEvents, order, eventData) }

private fun observableForChange(
    order: IOrder,
    eventData: OrderEventData
) = ReaderApi
    .ask<KForexUtils>()
    .map {
        val relay = PublishRelay.create<IOrderEvent>()
        with(it.handlerObservables) {
            eventRelays.accept(relay)
            configureObservable(relay, order, eventData).doOnComplete { completionTriggers.accept(Unit) }
        }
    }

private fun configureObservable(
    baseObservable: Observable<IOrderEvent>,
    order: IOrder,
    eventData: OrderEventData
) = baseObservable
    .filter { orderEvent -> orderEvent.order == order }
    .filter { orderEvent -> orderEvent.type in eventData.allEventTypes }
    .takeUntil { orderEvent -> orderEvent.type in eventData.finishEventTypes }
