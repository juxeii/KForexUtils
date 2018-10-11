package com.jforex.kforexutils.order.event.consumer

import com.jforex.kforexutils.order.event.OrderEvent
import com.jforex.kforexutils.order.event.consumer.data.OrderEventConsumerData
import com.jforex.kforexutils.rx.HotPublisher
import io.reactivex.rxkotlin.subscribeBy
import org.apache.logging.log4j.LogManager

class OrderEventConsumer(consumerData: OrderEventConsumerData)
{
    private val orderEventPublisher = HotPublisher<OrderEvent>()
    private val eventHandlers = consumerData.eventHandlers
    private val finishMessageTypes = consumerData.finishEventTypes
    private val basicActions = consumerData.basicActions
    private var isCompleted = false
    val type = consumerData.type

    private val logger = LogManager.getLogger(this.javaClass.name)

    fun onOrderEvent(oderEvent: OrderEvent) = orderEventPublisher.onNext(oderEvent)

    fun isCompleted() = isCompleted

    fun subscribe() = orderEventPublisher
        .observable()
        .doOnSubscribe { basicActions.onStart() }
        .doOnNext {
            logger.debug("Next jfOrder event with ${it.messageType} received on consumer type $type ")
        }
        .takeUntil { finishMessageTypes.contains(it.messageType) }
        .doFinally {
            logger.debug("OrderEventConsumer type $type finished ")
            isCompleted = true
        }
        .subscribeBy(
            onNext = {
                logger.debug("OrderEventConsumer onnext with ${it.messageType} called on consumer type $type")
                eventHandlers.getValue(it.messageType)(it)
            },
            onComplete = { basicActions.onComplete() },
            onError = { basicActions.onError(it) })
}