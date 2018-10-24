package com.jforex.kforexutils.order.event.handler

import com.jforex.kforexutils.order.event.OrderEvent
import com.jforex.kforexutils.rx.ObservableQueue
import io.reactivex.Observable

class OrderEventHandler(
    private val orderEvents: Observable<OrderEvent>,
    private val handlerQueue: ObservableQueue<OrderEvent>
) {
    fun observable() = orderEvents

    fun enqueue() = handlerQueue.enqueue()
}