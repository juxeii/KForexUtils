package com.jforex.kforexutils.engine

import com.dukascopy.api.IEngine
import com.dukascopy.api.IOrder
import com.jforex.kforexutils.misc.FieldProperty
import com.jforex.kforexutils.misc.KCallable
import com.jforex.kforexutils.misc.KForexUtils
import com.jforex.kforexutils.misc.KRunnable
import com.jforex.kforexutils.order.event.handler.OrderEventManager
import com.jforex.kforexutils.order.event.handler.data.OrderEventData
import com.jforex.kforexutils.order.extension.eventManager
import com.jforex.kforexutils.order.extension.kForexUtils
import com.jforex.kforexutils.order.task.runOrderTask

internal var IEngine.kForexUtils: KForexUtils by FieldProperty()

internal fun IEngine.createOrder(
    engineCall: KCallable<IOrder>,
    dataProvider: (KRunnable) -> OrderEventData
) {
    val handlerData = dataProvider { createOrder(engineCall, dataProvider) }
    val engineCallWithOrderInitialization = engineCallWithOrderInit(engineCall, handlerData)
    runOrderTask(engineCallWithOrderInitialization, handlerData.taskData).run(kForexUtils.context)
}

private fun IEngine.engineCallWithOrderInit(
    engineCall: KCallable<IOrder>,
    data: OrderEventData
) = {
    val order = engineCall()
    order.kForexUtils = kForexUtils
    val filteredOrderEvents = getOrderMessages(kForexUtils).filter { it.order == order }
    order.eventManager = OrderEventManager(filteredOrderEvents)
    order
        .eventManager
        .registerHandler(data)
    order
}

private fun getOrderMessages(utils: KForexUtils) = utils
    .orderMessageGateway
    .observable