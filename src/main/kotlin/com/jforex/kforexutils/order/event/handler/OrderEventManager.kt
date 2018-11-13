package com.jforex.kforexutils.order.event.handler

import com.jakewharton.rxrelay2.PublishRelay
import com.jforex.kforexutils.order.event.OrderEvent
import com.jforex.kforexutils.order.event.subscribeToOrderEvents
import com.jforex.kforexutils.order.params.actions.OrderEventExecutionParams
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.zipWith

class OrderEventManager(private val orderEvents: Observable<OrderEvent>) {
    private val completionTriggers: PublishRelay<Unit> = PublishRelay.create()
    private val changeEventHandlers: PublishRelay<OrderEventExecutionParams> = PublishRelay.create()

    init {
        completionTriggers
            .zipWith(changeEventHandlers)
            .map { it.second }
            .subscribeBy(onNext = { subscribeToOrderEvents(it) { completeTask() }.run(orderEvents) })

        completeTask()
    }

    private fun completeTask() = completionTriggers.accept(Unit)

    fun registerHandler(params: OrderEventExecutionParams) {
        if (params.eventParams.eventData.handlerType == OrderEventHandlerType.CHANGE) changeEventHandlers.accept(params)
        else subscribeToOrderEvents(params).run(orderEvents)
    }
}
