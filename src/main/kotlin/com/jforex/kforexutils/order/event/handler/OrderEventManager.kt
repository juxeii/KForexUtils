package com.jforex.kforexutils.order.event.handler

import com.dukascopy.api.IOrder
import com.jakewharton.rxrelay2.PublishRelay
import com.jforex.kforexutils.misc.KRunnable
import com.jforex.kforexutils.order.event.OrderEvent
import com.jforex.kforexutils.order.event.subscribeToOrderEvents
import com.jforex.kforexutils.order.task.OrderTaskExecutionParams
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.zipWith

class OrderEventManager(private val orderEvents: Observable<OrderEvent>) {
    private val completionTriggers: PublishRelay<Unit> = PublishRelay.create()
    private val changeEventHandlers: PublishRelay<ExecutionData> = PublishRelay.create()

    init {
        completionTriggers
            .zipWith(changeEventHandlers)
            .map { it.second }
            .subscribeBy(onNext = { subscribeToEvents(it) { completeTask() } })

        completeTask()
    }

    private fun completeTask() = completionTriggers.accept(Unit)

    fun registerHandler(
        order: IOrder,
        params: OrderTaskExecutionParams
    ) {
        val executionData = ExecutionData(order, params)
        if (params.eventParams.eventData.handlerType == OrderEventHandlerType.CHANGE)
            changeEventHandlers.accept(executionData)
        else subscribeToEvents(executionData) {}
    }

    private fun subscribeToEvents(
        data: ExecutionData,
        completionCall: KRunnable
    ) {
        subscribeToOrderEvents(data.params) { completionCall() }.run(orderEvents.filter { it.order == data.order })
    }
}

data class ExecutionData(
    val order: IOrder,
    val params: OrderTaskExecutionParams
)