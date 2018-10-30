package com.jforex.kforexutils.order.event.handler

import com.jforex.kforexutils.order.event.OrderEventObservable
import com.jforex.kforexutils.order.event.handler.data.OrderEventHandlerData
import java.util.concurrent.ConcurrentLinkedQueue

class OrderEventHandlerQueue(private val orderEventObservable: OrderEventObservable)
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
        orderEventObservable.subscribe(handlerData, ::subscribeNext)
}