package com.jforex.kforexutils.order.task

import com.dukascopy.api.IContext
import com.dukascopy.api.IOrder
import com.jforex.kforexutils.context.executeTaskOnStrategyThreadBlocking

internal fun runOrderTask(
    context: IContext,
    orderProvider: (params: OrderTaskExecutionParams) -> IOrder,
    taskParams: OrderTaskParams
) {
    val retryCall = { runOrderTask(context, orderProvider, taskParams) }
    val executionParams = OrderTaskExecutionParams(
        eventParams = taskParams.eventParams,
        retryCall = retryCall,
        retryHandler = taskParams.callParams.retryHandler
    )
    with(taskParams.callParams.callActions) {
        onStart()
        context
            .executeTaskOnStrategyThreadBlocking { orderProvider(executionParams) }
            .fold({ onError(it) }, { onSuccess(it) })
    }
}