package com.jforex.kforexutils.order.task

import arrow.core.Eval
import arrow.data.ReaderApi
import arrow.data.map
import arrow.data.runId
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

private data class TaskCallResult(
    val order: IOrder,
    val observable: Observable<IOrderEvent>
)

private data class ObservableParams(
    val order: IOrder,
    val eventData: OrderEventData
)

internal fun runOrderTask(
    orderCallable: KCallable<IOrder>,
    taskParams: OrderTaskParams
) = createStrategyCallable(taskParams.eventData, orderCallable)
    .map { callable ->
        with(taskParams.callHandlers) {
            Single
                .fromCallable { callable.value() }
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
        val extendedCallable = Eval.always {
            val order = orderCallable()
            createBaseObservable(ObservableParams(order, eventData))
                .map { TaskCallResult(order, it) }
                .runId(kForexUtils)
        }
        Eval.always { executeTaskOnStrategyThreadBlocking { extendedCallable.value() }.runId(kForexUtils) }
    }

private fun createBaseObservable(params: ObservableParams) =
    if (params.eventData.handlerType != OrderEventHandlerType.CHANGE) observableForNonChange(params)
    else observableForChange(params)

private fun observableForNonChange(params: ObservableParams) = ReaderApi
    .ask<KForexUtils>()
    .map { configureObservable(it.orderEvents, params) }

private fun observableForChange(params: ObservableParams) = ReaderApi
    .ask<KForexUtils>()
    .map {
        val relay = PublishRelay.create<IOrderEvent>()
        with(it.handlerObservables) {
            eventRelays.accept(relay)
            configureObservable(relay, params).doOnComplete { completionTriggers.accept(Unit) }
        }
    }

private fun configureObservable(
    baseObservable: Observable<IOrderEvent>,
    params: ObservableParams
) = baseObservable
    .filter { orderEvent -> orderEvent.order == params.order }
    .filter { orderEvent -> orderEvent.type in params.eventData.allEventTypes }
    .takeUntil { orderEvent -> orderEvent.type in params.eventData.finishEventTypes }
