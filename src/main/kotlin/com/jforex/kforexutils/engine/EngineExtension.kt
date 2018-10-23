package com.jforex.kforexutils.engine

import com.dukascopy.api.IEngine
import com.dukascopy.api.IOrder
import com.jforex.kforexutils.misc.FieldProperty
import com.jforex.kforexutils.misc.KCallable
import com.jforex.kforexutils.order.event.OrderEventGateway
import com.jforex.kforexutils.order.event.handler.OrderEventHandler
import com.jforex.kforexutils.order.event.handler.data.OrderEventHandlerData
import com.jforex.kforexutils.order.extension.eventHandler
import com.jforex.kforexutils.order.extension.taskRunner
import com.jforex.kforexutils.order.task.OrderTaskRunner

internal var IEngine.taskRunner: OrderTaskRunner by FieldProperty()
internal var IEngine.orderMessageGateway: OrderEventGateway by FieldProperty()

internal fun IEngine.createOrder(
    engineCall: KCallable<IOrder>,
    handlerData: OrderEventHandlerData
)
{
    val engineCallWithOrderInitialization = {
        val order = engineCall()
        val obs = orderMessageGateway
            .observable
            .filter { it.order == order }
        order.taskRunner = taskRunner
        order.eventHandler = OrderEventHandler(obs)
        order.eventHandler.observable()
    }

    taskRunner.run(engineCallWithOrderInitialization, handlerData)
}