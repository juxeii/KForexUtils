package com.jforex.kforexutils.order.event.handler

import com.jforex.kforexutils.order.event.OrderEventObservable
import com.jforex.kforexutils.order.event.handler.data.OrderEventHandlerData

class OrderEventHandler(
    private val orderEventObservable: OrderEventObservable,
    private val handlerQueue: OrderEventHandlerQueue
)
{
    fun register(handlerData: OrderEventHandlerData)
    {
        if (handlerData.type == OrderEventHandlerType.CHANGE) handlerQueue.add(handlerData)
        else orderEventObservable.subscribe(handlerData)
    }
}