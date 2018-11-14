package com.jforex.kforexutils.engine

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.misc.KCallable
import com.jforex.kforexutils.misc.KForexUtils
import com.jforex.kforexutils.order.extension.kForexUtils
import com.jforex.kforexutils.order.task.OrderTaskExecutionParams
import com.jforex.kforexutils.order.task.OrderTaskParams
import com.jforex.kforexutils.order.task.runOrderTask

internal fun createOrder(
    kForexUtils: KForexUtils,
    orderCreationCall: KCallable<IOrder>,
    taskParams: OrderTaskParams
) {
    val engineCallWithOrderInitialization = { executionParams: OrderTaskExecutionParams ->
        val order = orderCreationCall()
        order.kForexUtils = kForexUtils
        order.kForexUtils.registerHandler(order, executionParams)
        order
    }
    runOrderTask(
        kForexUtils.context,
        engineCallWithOrderInitialization,
        taskParams
    )
}