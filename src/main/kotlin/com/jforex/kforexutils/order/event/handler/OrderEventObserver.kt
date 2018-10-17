package com.jforex.kforexutils.order.event.handler

import com.jforex.kforexutils.misc.KRunnable
import com.jforex.kforexutils.order.event.OrderEvent
import com.jforex.kforexutils.order.event.handler.data.OrderEventHandlerData
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import org.apache.logging.log4j.LogManager

class OrderEventObserver(consumerData: OrderEventHandlerData)
{
    val type = consumerData.type
    private val eventHandlers = consumerData.eventHandlers
    private val finishMessageTypes = consumerData.finishEventTypes
    private val basicActions = consumerData.basicActions

    private val logger = LogManager.getLogger(this.javaClass.name)

    fun subscribe(
        orderEvents: Observable<OrderEvent>,
        onDone: KRunnable = {}
    ) = orderEvents
        .doOnSubscribe { basicActions.onStart() }
        .filter { eventHandlers.containsKey(it.type) }
        .takeUntil { finishMessageTypes.contains(it.type) }
        .doFinally {
            logger.debug("OrderEventObserver type $type finished ")
            onDone()
        }
        .subscribeBy(
            onNext = {
                logger.debug("OrderEventObserver onnext with ${it.type} called on handler type $type")
                eventHandlers.getValue(it.type)(it)
            },
            onComplete = { basicActions.onComplete() },
            onError = { basicActions.onError(it) })
}