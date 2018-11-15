package com.jforex.kforexutils.order.task

import arrow.data.Reader
import arrow.data.ReaderApi
import arrow.data.flatMap
import arrow.data.map
import com.dukascopy.api.IOrder
import com.jforex.kforexutils.context.executeTaskOnStrategyThreadBlocking
import com.jforex.kforexutils.misc.KCallable
import com.jforex.kforexutils.misc.KForexUtils
import com.jforex.kforexutils.misc.registerHandler

internal fun runOrderTask(
    orderCallable: KCallable<IOrder>,
    taskParams: OrderTaskParams
): Reader<KForexUtils, Unit> =
    ReaderApi
        .ask<KForexUtils>()
        .flatMap { createExecutionParams(orderCallable, taskParams) }
        .flatMap { createStrategyCallable(it, orderCallable) }
        .flatMap { run(taskParams.callParams.callActions, it) }

private fun createExecutionParams(
    orderCallable: KCallable<IOrder>,
    taskParams: OrderTaskParams
) = ReaderApi
    .ask<KForexUtils>()
    .map { kForexUtils ->
        OrderTaskExecutionParams(
            eventParams = taskParams.eventParams,
            retryCall = { runOrderTask(orderCallable, taskParams).run(kForexUtils) },
            retryHandler = taskParams.callParams.retryHandler
        )
    }

private fun createStrategyCallable(
    executionParams: OrderTaskExecutionParams,
    orderCallable: KCallable<IOrder>
) = ReaderApi
    .ask<KForexUtils>()
    .map { kForexUtils ->
        {
            val order = orderCallable()
            registerHandler(order, executionParams).run(kForexUtils)
            order
        }
    }

private fun run(
    callActions: OrderCallActions,
    orderCallable: KCallable<IOrder>
) = with(callActions) {
    ReaderApi
        .ask<KForexUtils>()
        .map { onStart() }
        .flatMap { executeTaskOnStrategyThreadBlocking(orderCallable) }
        .map { orderTry -> orderTry.fold({ onError(it) }, { onSuccess(it) }) }
}

