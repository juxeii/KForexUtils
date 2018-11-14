package com.jforex.kforexutils.engine

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.misc.KCallable
import com.jforex.kforexutils.misc.KForexUtils
import com.jforex.kforexutils.order.event.handler.OrderEventManager
import com.jforex.kforexutils.order.extension.eventManager
import com.jforex.kforexutils.order.extension.kForexUtils
import com.jforex.kforexutils.order.task.OrderTaskExecutionParams
import com.jforex.kforexutils.order.task.OrderTaskParams
import com.jforex.kforexutils.order.task.runOrderTask

internal fun createOrder(
    kForexUtils: KForexUtils,
    engineCall: KCallable<IOrder>,
    taskParams: OrderTaskParams
) {
    val engineCallWithOrderInitialization = { executionParams: OrderTaskExecutionParams ->
        val order = engineCall()
        order.kForexUtils = kForexUtils
        val filteredOrderEvents = kForexUtils
            .orderMessageGateway
            .observable.filter { it.order == order }
        order.eventManager = OrderEventManager(filteredOrderEvents)
        order
            .eventManager
            .registerHandler(executionParams)
        order
    }
    runOrderTask(
        kForexUtils.context,
        engineCallWithOrderInitialization,
        taskParams
    )
}