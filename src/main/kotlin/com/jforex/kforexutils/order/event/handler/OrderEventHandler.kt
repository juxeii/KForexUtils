package com.jforex.kforexutils.order.event.handler

import com.jforex.kforexutils.order.event.OrderEvent
import com.jforex.kforexutils.order.event.handler.data.OrderEventHandlerData
import com.jforex.kforexutils.order.event.subscribeToOrderEvents
import io.reactivex.Observable

class OrderEventHandler(
    private val orderEvents: Observable<OrderEvent>,
    private val handlerQueue: OrderEventHandlerQueue
) {
    fun register(handlerData: OrderEventHandlerData) {
        if (handlerData.type == OrderEventHandlerType.CHANGE) handlerQueue.add(handlerData)
        else subscribeToOrderEvents(handlerData).run(orderEvents)
    }
}