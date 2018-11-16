package com.jforex.kforexutils.order.task

import arrow.data.ReaderApi
import arrow.data.flatMap
import arrow.data.map
import com.dukascopy.api.IOrder
import com.jforex.kforexutils.context.executeTaskOnStrategyThreadBlocking
import com.jforex.kforexutils.misc.KCallable
import com.jforex.kforexutils.misc.KForexUtils
import com.jforex.kforexutils.order.event.handler.registerEventHandlerParams

internal fun runOrderTask(
    orderCallable: KCallable<IOrder>,
    taskParams: OrderTaskParams
) = ReaderApi
    .ask<KForexUtils>()
    .map { createStrategyCallable(taskParams, orderCallable) }
    .flatMap { run(taskParams.callHandlers, it) }

private fun createStrategyCallable(
    taskParams: OrderTaskParams,
    orderCallable: KCallable<IOrder>
) = {
    val order = orderCallable()
    registerEventHandlerParams(order, taskParams.eventHandlerParams)
    order
}

private fun run(
    callHandlers: OrderCallHandlers,
    orderCallable: KCallable<IOrder>
) = with(callHandlers) {
    ReaderApi
        .ask<KForexUtils>()
        .map { onStart() }
        .flatMap { executeTaskOnStrategyThreadBlocking(orderCallable) }
        .map { orderTry -> orderTry.fold({ onError(it) }, { onSuccess(it) }) }
}

