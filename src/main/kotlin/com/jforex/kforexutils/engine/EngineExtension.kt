package com.jforex.kforexutils.engine

import com.dukascopy.api.IEngine
import com.dukascopy.api.IOrder
import com.jforex.kforexutils.misc.FieldProperty
import com.jforex.kforexutils.misc.KCallable
import com.jforex.kforexutils.order.event.OrderEventGateway
import com.jforex.kforexutils.order.event.OrderEventHandler
import com.jforex.kforexutils.order.event.handler.OrderTaskRunner
import com.jforex.kforexutils.order.event.handler.data.OrderEventHandlerData
import com.jforex.kforexutils.order.extension.eventHandler
import com.jforex.kforexutils.order.extension.taskRunner

internal var IEngine.taskRunner: OrderTaskRunner by FieldProperty<IEngine, OrderTaskRunner>()
internal var IEngine.orderMessageGateway: OrderEventGateway by FieldProperty<IEngine, OrderEventGateway>()

internal fun IEngine.createOrder(
    engineCall: KCallable<IOrder>,
    handlerData: OrderEventHandlerData
) {
    val engineCallWithOrderInitialization = {
        with(engineCall()) {
            taskRunner = taskRunner
            eventHandler = OrderEventHandler(orderMessageGateway
                .observable
                .filter { it.order == this })
            eventHandler.observable()
        }
    }

    taskRunner.run(engineCallWithOrderInitialization, handlerData)
}