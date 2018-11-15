package com.jforex.kforexutils.order.task

import arrow.data.ReaderApi
import arrow.data.flatMap
import arrow.data.map
import com.dukascopy.api.IOrder
import com.jforex.kforexutils.context.executeTaskOnStrategyThreadBlocking
import com.jforex.kforexutils.misc.KCallable
import com.jforex.kforexutils.misc.KForexUtils
import com.jforex.kforexutils.order.event.handler.registerHandler

internal fun runOrderTask(
    orderCallable: KCallable<IOrder>,
    taskParams: OrderTaskParams
) = ReaderApi
    .ask<KForexUtils>()
    .flatMap { createStrategyCallable(taskParams, orderCallable) }
    .flatMap { run(taskParams.callActions, it) }

private fun createStrategyCallable(
    taskParams: OrderTaskParams,
    orderCallable: KCallable<IOrder>
) = ReaderApi
    .ask<KForexUtils>()
    .map { kForexUtils ->
        {
            val order = orderCallable()
            registerHandler(order, taskParams.eventParams).run(kForexUtils)
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

