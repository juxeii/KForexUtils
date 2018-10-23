package com.jforex.kforexutils.order.task

import arrow.effects.DeferredK
import arrow.effects.runAsync
import com.dukascopy.api.IContext
import com.jforex.kforexutils.context.deferredTask
import com.jforex.kforexutils.misc.KCallable
import com.jforex.kforexutils.misc.KRunnable
import com.jforex.kforexutils.order.event.OrderEvent
import com.jforex.kforexutils.order.event.handler.data.OrderEventHandlerData
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import org.apache.logging.log4j.LogManager

class OrderTaskRunner(private val context: IContext)
{
    private val logger = LogManager.getLogger(this.javaClass.name)

    fun run(
        call: KCallable<Observable<OrderEvent>>,
        handlerData: OrderEventHandlerData
    )
    {
        val thisCallForRetry = { run(call, handlerData) }
        handlerData.run {
            context
                .deferredTask {
                    basicActions.onStart()
                    call()
                }.runAsync { result ->
                    result.fold(
                        { DeferredK { basicActions.onError(it) } },
                        { DeferredK { configureObservable(it, handlerData, thisCallForRetry) } })
                }
        }
    }

    private fun configureObservable(
        observable: Observable<OrderEvent>,
        handlerData: OrderEventHandlerData,
        callForRetry: KRunnable
    )
    {
        handlerData.run {
            observable
                .filter { it.type in eventHandlers }
                .takeUntil { it.type in finishEventTypes }
                .subscribeBy(
                    onNext = {
                        when (it.type)
                        {
                            rejectEventType -> basicActions.taskRetry?.onRejectEvent(it, callForRetry)
                            else -> eventHandlers.getValue(it.type)(it)
                        }
                    })
        }
    }
}