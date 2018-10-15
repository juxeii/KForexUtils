package com.jforex.kforexutils.order.event.consumer

import com.jforex.kforexutils.misc.KRunnable
import com.jforex.kforexutils.order.event.OrderEvent
import com.jforex.kforexutils.order.event.consumer.data.OrderEventConsumerData
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import org.apache.logging.log4j.LogManager

class OrderEventHandler(consumerData: OrderEventConsumerData) {
    private val eventHandlers = consumerData.eventHandlers
    private val finishMessageTypes = consumerData.finishEventTypes
    private val basicActions = consumerData.basicActions
    val type = consumerData.type

    private val logger = LogManager.getLogger(this.javaClass.name)

    fun subscribe(orderEvents: Observable<OrderEvent>, onDone: KRunnable = {}) = orderEvents
        .doOnSubscribe { basicActions.onStart() }
        .filter { eventHandlers.containsKey(it.messageType) }
        .doOnNext {
            logger.debug("Next order event with ${it.messageType} received on consumer type $type ")
        }
        .takeUntil { finishMessageTypes.contains(it.messageType) }
        .doFinally {
            logger.debug("OrderEventHandler type $type finished ")
            onDone()
        }
        .subscribeBy(
            onNext = {
                logger.debug("OrderEventHandler onnext with ${it.messageType} called on consumer type $type")
                eventHandlers.getValue(it.messageType)(it)
            },
            onComplete = { basicActions.onComplete() },
            onError = { basicActions.onError(it) })
}