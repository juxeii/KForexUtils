package com.jforex.kforexutils.order.event.handler

import com.jforex.kforexutils.order.event.OrderEvent
import com.jforex.kforexutils.order.event.handler.data.OrderEventHandlerData
import com.jforex.kforexutils.order.event.subscribeToOrderEvents
import io.reactivex.Observable
import java.util.concurrent.ConcurrentLinkedQueue

class OrderEventHandlerQueue(private val orderEvents: Observable<OrderEvent>)
{
    private var queuedHandlerData = ConcurrentLinkedQueue<OrderEventHandlerData>()

    @Synchronized
    fun add(handlerData: OrderEventHandlerData)
    {
        if (queuedHandlerData.isEmpty()) subscribeHandler(handlerData)
        queuedHandlerData.add(handlerData)
    }

    @Synchronized
    private fun subscribeNext()
    {
        queuedHandlerData.poll()
        if (!queuedHandlerData.isEmpty()) subscribeHandler(queuedHandlerData.peek())
    }

    private fun subscribeHandler(handlerData: OrderEventHandlerData) =
        subscribeToOrderEvents(handlerData, ::subscribeNext).run(orderEvents)
}