package com.jforex.kforexutils.order.task

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.context.executeTaskOnStrategyThreadBlocking
import com.jforex.kforexutils.misc.KCallable
import com.jforex.kforexutils.misc.KForexUtils
import com.jforex.kforexutils.misc.KRunnable

internal fun runOrderTask(
    kForexUtils: KForexUtils,
    orderCallable: KCallable<IOrder>,
    taskParams: OrderTaskParams
) {
    val executionParams = taskToExecutionParams(
        taskParams = taskParams,
        retryCall = { runOrderTask(kForexUtils, orderCallable, taskParams) }
    )
    val callWithHandlerRegistration = {
        val order = orderCallable()
        kForexUtils
            .eventManager
            .registerHandler(order, executionParams)
        order
    }
    with(taskParams.callParams.callActions) {
        onStart()
        kForexUtils
            .context
            .executeTaskOnStrategyThreadBlocking { callWithHandlerRegistration() }
            .fold({ onError(it) }, { onSuccess(it) })
    }
}

private fun taskToExecutionParams(
    taskParams: OrderTaskParams,
    retryCall: KRunnable
) = OrderTaskExecutionParams(
    eventParams = taskParams.eventParams,
    retryCall = retryCall,
    retryHandler = taskParams.callParams.retryHandler
)