package com.jforex.kforexutils.order.task

import com.dukascopy.api.IContext
import com.jforex.kforexutils.context.deferOnStrategyThread
import com.jforex.kforexutils.misc.KCallable
import com.jforex.kforexutils.order.event.OrderEvent
import com.jforex.kforexutils.order.event.handler.data.OrderEventHandlerData
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import org.apache.logging.log4j.LogManager

class OrderTaskRunner(private val context: IContext)
{
    private val logger = LogManager.getLogger(this.javaClass.name)

    fun run(
        orderEventsProvider: KCallable<Observable<OrderEvent>>,
        handlerData: OrderEventHandlerData
    )
    {
        val thisCallForRetry = { run(orderEventsProvider, handlerData) }
        with(handlerData) {
            context
                .deferOnStrategyThread(orderEventsProvider)
                .flatMapObservable { it }
                .filter { it.type in eventHandlers }
                .takeUntil { it.type in finishEventTypes }
                .subscribeBy(
                    onNext = {
                        if (it.type == rejectEventType) basicActions.taskRetry?.onRejectEvent(it, thisCallForRetry)
                        else eventHandlers.getValue(it.type)(it)
                    },
                    onError = { basicActions.onError(it) })
        }
    }
}