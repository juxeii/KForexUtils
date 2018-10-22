package com.jforex.kforexutils.order

import com.jforex.kforexutils.misc.KCallable
import com.jforex.kforexutils.order.event.OrderEvent
import com.jforex.kforexutils.order.event.handler.data.OrderEventHandlerData
import com.jforex.kforexutils.rx.RejectException
import com.jforex.kforexutils.thread.StrategyThread
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy

class OrderTaskRunner(private val strategyThread: StrategyThread) {
    fun run(
        call: KCallable<Observable<OrderEvent>>,
        handlerData: OrderEventHandlerData
    ) {
        handlerData.run {
            val basicObservable = strategyThread
                .observeCallable(call)
                .doOnSubscribe { basicActions.onStart() }
                .flatMapObservable { it }
                .filter { eventHandlers.containsKey(it.type) }
                .takeUntil { finishEventTypes.contains(it.type) }

            basicActions
                .retryObservable
                .fold({ subscribe(basicObservable, handlerData) },
                    {
                        val observableWithRetry = basicObservable
                            .flatMap {
                                if (it.type == rejectEventType) Observable.error(RejectException())
                                else Observable.just(it)
                            }
                            .retryWhen { it }
                        subscribe(observableWithRetry, handlerData)
                    })
        }
    }

    private fun subscribe(
        observable: Observable<OrderEvent>,
        handlerData: OrderEventHandlerData
    ) {
        handlerData.run {
            observable
                .subscribeBy(
                    onNext = { eventHandlers.getValue(it.type)(it) },
                    onComplete = { basicActions.onComplete() },
                    onError = { basicActions.onError(it) })
        }
    }
}