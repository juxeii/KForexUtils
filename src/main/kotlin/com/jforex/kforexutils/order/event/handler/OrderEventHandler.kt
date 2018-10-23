package com.jforex.kforexutils.order.event.handler

import com.dukascopy.api.IOrder
import com.jakewharton.rxrelay2.PublishRelay
import com.jforex.kforexutils.misc.OrderEventConsumer
import com.jforex.kforexutils.order.event.OrderEvent
import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.extension.isCanceled
import com.jforex.kforexutils.order.extension.isClosed
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import org.apache.logging.log4j.LogManager
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.ConcurrentMap

class OrderEventHandler(private val orderEvents: Observable<OrderEvent>) {
    private var fixedHandlers: ConcurrentMap<OrderEventType, OrderEventConsumer> = ConcurrentHashMap()
    private var queuedHandlers =
        mapOf(
            OrderEventType.CHANGED_SL to ConcurrentLinkedQueue<OrderEventConsumer>(),
            OrderEventType.CHANGED_TP to ConcurrentLinkedQueue<OrderEventConsumer>(),
            OrderEventType.CHANGED_LABEL to ConcurrentLinkedQueue<OrderEventConsumer>(),
            OrderEventType.CHANGED_TP to ConcurrentLinkedQueue<OrderEventConsumer>(),
            OrderEventType.CHANGED_TP to ConcurrentLinkedQueue<OrderEventConsumer>(),
            OrderEventType.CHANGED_TP to ConcurrentLinkedQueue<OrderEventConsumer>(),
            OrderEventType.CHANGED_TP to ConcurrentLinkedQueue<OrderEventConsumer>(),
            OrderEventType.CHANGED_GTT to ConcurrentLinkedQueue<OrderEventConsumer>()
        )

    private val logger = LogManager.getLogger(this.javaClass.name)

    init {
        orderEvents
            .takeUntil { isOrderInactive(it.order) }
            .subscribeBy(onNext = { onNextEvent(it) })
    }

    private fun isOrderInactive(order: IOrder) = order.isClosed || order.isCanceled

    private fun onNextEvent(orderEvent: OrderEvent) {
        if (fixedHandlers.contains(orderEvent.type)) {
            fixedHandlers[orderEvent.type]?.invoke(orderEvent)
        } else if (queuedHandlers.contains(orderEvent.type)) {
            queuedHandlers[orderEvent.type]?.poll()?.invoke(orderEvent)
        }
    }

    fun registerHandlers(handlers: Map<OrderEventType, OrderEventConsumer>): Observable<OrderEvent> {
        val relay: PublishRelay<OrderEvent> = PublishRelay.create()
        eventObservers.add(relay)
        logger.debug("Added relay to observer queue.")
        return relay
    }
}