package com.jforex.kforexutils.order.event

import com.jforex.kforexutils.order.event.handler.OrderEventObserver
import com.jforex.kforexutils.order.event.handler.data.OrderEventHandlerData
import io.reactivex.Observable
import org.apache.logging.log4j.LogManager
import java.util.concurrent.ConcurrentLinkedQueue

class OrderEventHandler(private val orderEvents: Observable<OrderEvent>) {
    private var eventObservers = ConcurrentLinkedQueue<OrderEventObserver>()

    private val logger = LogManager.getLogger(this.javaClass.name)

    fun enqueueEventObserver(handlerData: OrderEventHandlerData) {
        val eventObserver = OrderEventObserver(handlerData)
        if (eventObservers.isEmpty())
            subscribeObserver(eventObserver)
        logger.debug("Event observer with type ${eventObserver.type} gets added to observer queue.")
        eventObservers.add(eventObserver)
    }

    private fun subscribeObserver(observer: OrderEventObserver) {
        logger.debug("Event observer with type ${observer.type} subscribes to order events.")
        observer.subscribe(orderEvents) { onObserverDoneSubscribeNext() }
    }

    private fun onObserverDoneSubscribeNext() {
        if (eventObservers.isEmpty()) return

        val currentObserver = eventObservers.remove()
        logger.debug("Removed current event observer with type ${currentObserver.type} from queue.")
        if (!eventObservers.isEmpty())
            subscribeObserver(eventObservers.element())
    }
}