package com.jforex.kforexutils.order

import arrow.effects.DeferredK
import arrow.effects.runAsync
import com.jforex.kforexutils.misc.KCallable
import com.jforex.kforexutils.order.event.OrderEvent
import com.jforex.kforexutils.order.event.handler.data.OrderEventHandlerData
import com.jforex.kforexutils.rx.RejectException
import com.jforex.kforexutils.thread.StrategyThread
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import org.apache.logging.log4j.LogManager

class OrderTaskRunner(private val strategyThread: StrategyThread)
{
    private val logger = LogManager.getLogger(this.javaClass.name)

    fun run(
        call: KCallable<Observable<OrderEvent>>,
        handlerData: OrderEventHandlerData
    )
    {
        handlerData.run {
            strategyThread
                .defer {
                    basicActions.onStart()
                    call()
                }.runAsync { result ->
                    result.fold(
                        { DeferredK { basicActions.onError(it) } },
                        { DeferredK { configureObservable(it, handlerData) } })
                }
        }
    }

    private fun configureObservable(
        observable: Observable<OrderEvent>,
        handlerData: OrderEventHandlerData
    )
    {
        handlerData.run {
            val basicObservable = observable
                .filter { eventHandlers.containsKey(it.type) }
                .takeUntil { finishEventTypes.contains(it.type) }

            basicActions.retryObservable
                .fold({ subscribe(basicObservable, handlerData) },
                    { observable ->
                        val observableWithRetry = basicObservable
                            .flatMap { orderEvent ->
                                if (orderEvent.type == rejectEventType) Observable.error(RejectException())
                                else Observable.just(orderEvent)
                            }
                            .retryWhen { observable }
                        subscribe(observableWithRetry, handlerData)
                    })
        }
    }

    private fun subscribe(
        observable: Observable<OrderEvent>,
        handlerData: OrderEventHandlerData
    )
    {
        handlerData.run {
            observable
                .subscribeBy(
                    onNext = { eventHandlers.getValue(it.type)(it) },
                    onComplete = { basicActions.onComplete() },
                    onError = { basicActions.onError(it) })
        }
    }
}