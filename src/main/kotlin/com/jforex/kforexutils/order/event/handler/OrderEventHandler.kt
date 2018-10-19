package com.jforex.kforexutils.order.event.handler

import com.dukascopy.api.IOrder
import com.jakewharton.rxrelay2.PublishRelay
import com.jforex.kforexutils.order.event.OrderEvent
import com.jforex.kforexutils.order.extension.isCanceled
import com.jforex.kforexutils.order.extension.isClosed
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import org.apache.logging.log4j.LogManager
import java.util.concurrent.ConcurrentLinkedQueue

class OrderEventHandler(private val orderEvents: Observable<OrderEvent>) {
    private var eventObservers = ConcurrentLinkedQueue<PublishRelay<OrderEvent>>()

    private val logger = LogManager.getLogger(this.javaClass.name)

    init {
        orderEvents
            .takeUntil { isOrderInactive(it.order) }
            .subscribeBy(onNext = { onNextEvent(it) })
    }

    private fun isOrderInactive(order: IOrder) = order.isClosed || order.isCanceled

    private fun onNextEvent(orderEvent: OrderEvent) {
        logger.debug("eventObservers.isEmpty() ${eventObservers.isEmpty()}")
        if (eventObservers.isEmpty()) return
        logger.debug("Onnext event ${orderEvent.type} received.")

        val relay = eventObservers.element()
        if (relay.hasObservers()) {
            logger.debug("Current relay has observers, so event gets pushed")
            relay.accept(orderEvent)
        } else {
            logger.debug("Current relay has no more observers, so event gets pushed to next observer")
            eventObservers.remove()
            eventObservers.peek()?.accept(orderEvent)
        }
    }

    fun observable(): Observable<OrderEvent> {
        val relay: PublishRelay<OrderEvent> = PublishRelay.create()
        eventObservers.add(relay)
        logger.debug("Added relayto observer queue.")
        return relay
    }
}