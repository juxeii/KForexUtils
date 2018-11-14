package com.jforex.kforexutils.order.task

import com.dukascopy.api.IContext
import com.dukascopy.api.IOrder
import com.jforex.kforexutils.context.executeTaskOnStrategyThreadBlocking
import com.jforex.kforexutils.misc.KRunnable

internal fun runOrderTask(
    context: IContext,
    orderProvider: (params: OrderTaskExecutionParams) -> IOrder,
    taskParams: OrderTaskParams
) {
    val executionParams = taskToExecutionParams(
        taskParams = taskParams,
        retryCall = { runOrderTask(context, orderProvider, taskParams) }
    )
    with(taskParams.callParams.callActions) {
        onStart()
        context
            .executeTaskOnStrategyThreadBlocking { orderProvider(executionParams) }
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