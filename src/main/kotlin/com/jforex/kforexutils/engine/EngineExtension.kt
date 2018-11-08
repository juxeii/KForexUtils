package com.jforex.kforexutils.engine

import com.dukascopy.api.IEngine
import com.dukascopy.api.IOrder
import com.jforex.kforexutils.misc.FieldProperty
import com.jforex.kforexutils.misc.KCallable
import com.jforex.kforexutils.misc.KForexUtils
import com.jforex.kforexutils.misc.KRunnable
import com.jforex.kforexutils.order.event.handler.OrderEventHandler
import com.jforex.kforexutils.order.event.handler.OrderEventHandlerQueue
import com.jforex.kforexutils.order.event.handler.data.OrderEventHandlerData
import com.jforex.kforexutils.order.extension.eventHandler
import com.jforex.kforexutils.order.extension.kForexUtils
import com.jforex.kforexutils.order.task.runOrderTask

internal var IEngine.kForexUtils: KForexUtils by FieldProperty()

internal fun IEngine.createOrder(
    engineCall: KCallable<IOrder>,
    handlerDataProvider: (KRunnable) -> OrderEventHandlerData
) {
    val handlerData = handlerDataProvider { createOrder(engineCall, handlerDataProvider) }
    val engineCallWithOrderInitialization = engineCallWithOrderInit(engineCall, handlerData)
    runOrderTask(engineCallWithOrderInitialization, handlerData).run(kForexUtils.context)
}

private fun IEngine.engineCallWithOrderInit(
    engineCall: KCallable<IOrder>,
    handlerData: OrderEventHandlerData
) = {
    val order = engineCall()
    val filteredOrderEvents = kForexUtils
        .orderMessageGateway
        .observable
        .filter { it.order == order }

    val handlerQueue = OrderEventHandlerQueue(filteredOrderEvents)
    order.kForexUtils = kForexUtils
    order.eventHandler = OrderEventHandler(filteredOrderEvents, handlerQueue)
    order.eventHandler.register(handlerData)
    order
}