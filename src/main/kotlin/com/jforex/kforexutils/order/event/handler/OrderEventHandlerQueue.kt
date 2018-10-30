package com.jforex.kforexutils.order.event.handler

import com.jforex.kforexutils.order.event.OrderEventObservable
import com.jforex.kforexutils.order.event.handler.data.OrderEventHandlerData
import org.apache.logging.log4j.LogManager
import java.util.concurrent.ConcurrentLinkedQueue

class OrderEventHandlerQueue(private val orderEventObservable: OrderEventObservable)
{
    private var queuedHandlerData = ConcurrentLinkedQueue<OrderEventHandlerData>()
    private val logger = LogManager.getLogger(this.javaClass.name)

    @Synchronized
    fun add(handlerData: OrderEventHandlerData)
    {
        logger.debug("Adding handlerData to queue.")
        if (queuedHandlerData.isEmpty()) orderEventObservable.subscribe(handlerData, ::subscribeNext)
        queuedHandlerData.add(handlerData)
    }

    @Synchronized
    private fun subscribeNext()
    {
        if (queuedHandlerData.isEmpty()) return

        logger.debug("Subscribing next observer")
        queuedHandlerData.poll()
        if (!queuedHandlerData.isEmpty())
        {
            val handlerData = queuedHandlerData.peek()
            orderEventObservable.subscribe(handlerData, ::subscribeNext)
        }
    }
}