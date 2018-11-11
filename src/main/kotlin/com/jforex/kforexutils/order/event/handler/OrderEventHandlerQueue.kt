package com.jforex.kforexutils.order.event.handler

import arrow.core.toT
import arrow.data.State
import arrow.data.StateApi
import arrow.data.fix
import arrow.data.run
import arrow.instances.monad
import arrow.typeclasses.binding
import com.jforex.kforexutils.order.event.OrderEvent
import com.jforex.kforexutils.order.event.handler.data.OrderEventHandlerData
import com.jforex.kforexutils.order.event.subscribeToOrderEvents
import io.reactivex.Observable
import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue


internal fun addHandler(
    handlerData: OrderEventHandlerData,
    orderEvents: Observable<OrderEvent>
) = StateApi
    .monad<Queue<OrderEventHandlerData>>()
    .binding {
        subscribeHandlerWhenQueueIsEmpty(handlerData, orderEvents).bind()
        addSingleHandler(handlerData).bind()
    }.fix()

private fun subscribeHandlerWhenQueueIsEmpty(
    handlerData: OrderEventHandlerData,
    orderEvents: Observable<OrderEvent>
) = StateApi.modify<Queue<OrderEventHandlerData>> { queue ->
    if (queue.isEmpty()) subscribeHandler(handlerData, orderEvents).run(queue)
    queue
}

private fun addSingleHandler(handlerData: OrderEventHandlerData) =
    StateApi.modify<Queue<OrderEventHandlerData>> { queue ->
        queue.add(handlerData)
        queue
    }

private fun removeCurrentHandler() =
    StateApi.modify<Queue<OrderEventHandlerData>> { queue ->
        queue.poll()
        queue
    }

private fun subscribeHandler(
    handlerData: OrderEventHandlerData,
    orderEvents: Observable<OrderEvent>
): State<Queue<OrderEventHandlerData>, Unit> =
    State { queue ->
        val nextCall = {
            subscribeNextHandler(orderEvents).run(queue)
            Unit
        }
        subscribeToOrderEvents(handlerData, nextCall).run(orderEvents)
        queue toT Unit
    }

private fun subscribeNextHandler(orderEvents: Observable<OrderEvent>) =
    StateApi
        .monad<Queue<OrderEventHandlerData>>()
        .binding {
            removeCurrentHandler().bind()
            subscribeTopOfQueueHandler(orderEvents).bind()
        }.fix()

private fun subscribeTopOfQueueHandler(orderEvents: Observable<OrderEvent>) =
    State<Queue<OrderEventHandlerData>, Unit> { queue ->
        if (!queue.isEmpty()) subscribeHandler(queue.peek(), orderEvents).run(queue)
        queue toT Unit
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