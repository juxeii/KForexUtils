package com.jforex.kforexutils.engine

import com.dukascopy.api.IEngine
import com.dukascopy.api.IOrder
import com.jforex.kforexutils.misc.FieldProperty
import com.jforex.kforexutils.misc.KCallable
import com.jforex.kforexutils.order.event.OrderEventGateway
import com.jforex.kforexutils.order.event.OrderEventObservable
import com.jforex.kforexutils.order.event.handler.OrderEventHandler
import com.jforex.kforexutils.order.event.handler.OrderEventHandlerQueue
import com.jforex.kforexutils.order.event.handler.data.OrderEventHandlerData
import com.jforex.kforexutils.order.extension.eventHandler
import com.jforex.kforexutils.order.extension.taskRunner
import com.jforex.kforexutils.order.task.OrderTaskRunner

internal var IEngine.taskRunner: OrderTaskRunner by FieldProperty()
internal var IEngine.orderMessageGateway: OrderEventGateway by FieldProperty()

internal fun IEngine.createOrder(
    engineCall: KCallable<IOrder>,
    handlerData: OrderEventHandlerData
) {
    handlerData.retryCall = { createOrder(engineCall, handlerData) }
    val engineCallWithOrderInitialization = {
        val order = engineCall()
        val filteredOrderEvents = orderMessageGateway
            .observable
            .filter { it.order == order }
        order.taskRunner = taskRunner
        val orderEventObservable = OrderEventObservable(filteredOrderEvents)
        val handlerQueue = OrderEventHandlerQueue(orderEventObservable)
        order.eventHandler = OrderEventHandler(orderEventObservable, handlerQueue)
        order.eventHandler.register(handlerData)
        order
    }

    taskRunner.run(
        task = engineCallWithOrderInitialization,
        actions = handlerData.taskActions
    )
}