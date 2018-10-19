package com.jforex.kforexutils.order.event.handler

import com.jforex.kforexutils.misc.KCallable
import com.jforex.kforexutils.order.event.OrderEvent
import com.jforex.kforexutils.order.event.handler.data.OrderEventHandlerData
import com.jforex.kforexutils.thread.StrategyThread
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import org.apache.logging.log4j.LogManager

class OrderTaskRunner(private val strategyThread: StrategyThread) {
    private val logger = LogManager.getLogger(this.javaClass.name)

    fun run(
        call: KCallable<Observable<OrderEvent>>,
        handlerData: OrderEventHandlerData
    ) {
        val eventHandlers = handlerData.eventHandlers
        val finishEventTypes = handlerData.finishEventTypes
        val basicActions = handlerData.basicActions

        strategyThread
            .observeCallable(call)
            .doOnSubscribe { basicActions.onStart() }
            .flatMapObservable { it }
            .filter { eventHandlers.containsKey(it.type) }
            .takeUntil { finishEventTypes.contains(it.type) }
            .subscribeBy(
                onNext = {
                    logger.debug("OrderEventObserver onnext with ${it.type} called")
                    eventHandlers.getValue(it.type)(it)
                },
                onComplete = { basicActions.onComplete() },
                onError = { basicActions.onError(it) })
    }
}