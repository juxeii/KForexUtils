package com.jforex.kforexutils.order.event.handler

import arrow.core.toT
import arrow.data.State
import com.jforex.kforexutils.order.event.OrderEvent
import com.jforex.kforexutils.order.event.handler.data.OrderEventHandlerData
import com.jforex.kforexutils.order.event.subscribeToOrderEvents
import io.reactivex.Observable
import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue


internal fun addHandler(
    handlerData: OrderEventHandlerData,
    orderEvents: Observable<OrderEvent>
) = State<Queue<OrderEventHandlerData>, Unit> { queue ->
    if (queue.isEmpty())
    {
        subscribeHandler(handlerData, orderEvents, queue)
    }
    queue.add(handlerData)
    queue toT Unit
}


private fun subscribeHandler(
    handlerData: OrderEventHandlerData,
    orderEvents: Observable<OrderEvent>,
    queue: Queue<OrderEventHandlerData>
)
{
    val nextCall = { subscribeNextHandler(orderEvents, queue) }
    subscribeToOrderEvents(handlerData, nextCall).run(orderEvents)
}

private fun subscribeNextHandler(
    orderEvents: Observable<OrderEvent>,
    queue: Queue<OrderEventHandlerData>
)
{
    queue.poll()
    if (!queue.isEmpty()) subscribeHandler(queue.peek(), orderEvents, queue)
}

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