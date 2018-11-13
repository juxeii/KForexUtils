package com.jforex.kforexutils.order.event.handler

import com.jakewharton.rxrelay2.PublishRelay
import com.jforex.kforexutils.misc.KRunnable
import com.jforex.kforexutils.order.event.OrderEvent
import com.jforex.kforexutils.order.event.handler.data.OrderEventHandlerData
import com.jforex.kforexutils.order.event.subscribeToOrderEvents
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.zipWith

class OrderEventManager(private val orderEvents: Observable<OrderEvent>) {
    private val completionTriggers: PublishRelay<Unit> = PublishRelay.create()
    val eventHandlers: PublishRelay<OrderEventHandlerData> = PublishRelay.create()

    init {
        completionTriggers
            .zipWith(eventHandlers)
            .map { it.second }
            .subscribeBy(onNext = ::onNextEventHandler)

        completeTask()
    }

    private fun completeTask() = completionTriggers.accept(Unit)

    private fun onNextEventHandler(handlerData: OrderEventHandlerData) {
        val completionTask: KRunnable = if (handlerData.type == OrderEventHandlerType.CHANGE) {
            { completeTask() }
        } else {
            {}
        }
        subscribeToOrderEvents(handlerData, completionTask).run(orderEvents)
    }
}