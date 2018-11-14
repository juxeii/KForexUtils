package com.jforex.kforexutils.order

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.misc.KRunnable
import com.jforex.kforexutils.order.extension.eventManager
import com.jforex.kforexutils.order.extension.kForexUtils
import com.jforex.kforexutils.order.task.OrderTaskExecutionParams
import com.jforex.kforexutils.order.task.OrderTaskParams
import com.jforex.kforexutils.order.task.runOrderTask

internal fun changeOrder(
    order: IOrder,
    orderCall: KRunnable,
    taskParams: OrderTaskParams
) {
    val orderCallWithEventHandlerInitialization = { executionParams: OrderTaskExecutionParams ->
        orderCall()
        order.eventManager.registerHandler(executionParams)
        order
    }
    runOrderTask(
        order.kForexUtils.context,
        orderCallWithEventHandlerInitialization,
        taskParams
    )
}