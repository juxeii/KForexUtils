package com.jforex.kforexutils.order.task

import arrow.core.value
import arrow.data.Reader
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
    createCallable(taskParams.eventData, orderCallable)
        .map { callable ->
            Single
                .fromCallable(callable)
                .doOnSubscribe { onStart() }
                .doOnError { onError(it) }
                .doOnSuccess { onSuccess(it.order) }
                .flatMapObservable { it.observable }
        }
}

private fun createCallable(
    eventData: OrderEventData,
    orderCallable: KCallable<IOrder>
): Reader<KForexUtils, KCallable<TaskCallResult>> = ReaderApi
    .ask<KForexUtils>()
    .map { kForexUtils ->
        val callable = {
            val order = orderCallable()
            val observable = createBaseObservable(eventData)
                .run(kForexUtils)
                .value()
                .filter { orderEvent -> orderEvent.order == order }
                .filter { orderEvent -> orderEvent.type in eventData.allEventTypes }
                .takeUntil { orderEvent -> orderEvent.type in eventData.finishEventTypes }
                .doOnComplete {
                    if (eventData.handlerType == OrderEventHandlerType.CHANGE)
                    {
                        kForexUtils.handlerObservables.completionTriggers.accept(Unit)
                    }
                }
            TaskCallResult(order, observable)
        }
        { executeTaskOnStrategyThreadBlocking(kForexUtils, callable) }
    }

private fun createBaseObservable(eventData: OrderEventData) = ReaderApi
    .ask<KForexUtils>()
    .map { kForexUtils ->
        if (eventData.handlerType != OrderEventHandlerType.CHANGE) kForexUtils.orderEvents
        else
        {
            val relay = PublishRelay.create<IOrderEvent>()
            kForexUtils.handlerObservables.eventRelays.accept(relay)
            relay
        }
    }
