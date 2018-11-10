package com.jforex.kforexutils.order.event.handler

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.order.event.OrderEvent
import com.jforex.kforexutils.order.event.handler.data.OrderEventHandlerData
import com.jforex.kforexutils.order.event.subscribeToOrderEvents
import io.reactivex.Observable
import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue

typealias MappedHandlerQueue = Map<IOrder, Queue<OrderEventHandlerData>>

internal fun addHandler(
    order: IOrder,
    handlerData: OrderEventHandlerData,
    orderEvents: Observable<OrderEvent>,
    handlerQueues: MappedHandlerQueue
)
{
    val queue = handlerQueues.getValue(order)
    if (queue.isEmpty())
    {
        subscribeHandler(order, handlerData, orderEvents, handlerQueues)
    }
    queue.add(handlerData)
}

private fun subscribeHandler(
    order: IOrder,
    handlerData: OrderEventHandlerData,
    orderEvents: Observable<OrderEvent>,
    handlerQueues: MappedHandlerQueue
)
{
    val nextCall = { subscribeNextHandler(order, orderEvents, handlerQueues) }
    subscribeToOrderEvents(handlerData, nextCall).run(orderEvents)
}

private fun subscribeNextHandler(
    order: IOrder,
    orderEvents: Observable<OrderEvent>,
    handlerQueues: MappedHandlerQueue
)
{
    val queue = handlerQueues.getValue(order)
    queue.poll()
    if (!queue.isEmpty()) subscribeHandler(order, queue.peek(), orderEvents, handlerQueues)
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