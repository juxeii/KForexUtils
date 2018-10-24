package com.jforex.kforexutils.engine

import com.dukascopy.api.IEngine
import com.dukascopy.api.IOrder
import com.jforex.kforexutils.misc.FieldProperty
import com.jforex.kforexutils.misc.KCallable
import com.jforex.kforexutils.order.event.OrderEvent
import com.jforex.kforexutils.order.event.OrderEventGateway
import com.jforex.kforexutils.order.event.handler.OrderEventHandler
import com.jforex.kforexutils.order.event.handler.data.OrderEventHandlerData
import com.jforex.kforexutils.order.extension.eventHandler
import com.jforex.kforexutils.order.extension.taskRunner
import com.jforex.kforexutils.order.task.OrderTaskRunner
import com.jforex.kforexutils.rx.ObservableQueue

internal var IEngine.taskRunner: OrderTaskRunner by FieldProperty()
internal var IEngine.orderMessageGateway: OrderEventGateway by FieldProperty()

internal fun IEngine.createOrder(
    engineCall: KCallable<IOrder>,
    handlerData: OrderEventHandlerData
) {
    val engineCallWithOrderInitialization = {
        val order = engineCall()
        val obs = orderMessageGateway
            .observable
            .filter { it.order == order }
        order.taskRunner = taskRunner
        val handlerQueue = ObservableQueue<OrderEvent>(obs)
        order.eventHandler = OrderEventHandler(obs, handlerQueue)
        order.eventHandler.observable()
    }

    taskRunner.run(engineCallWithOrderInitialization, handlerData)
}