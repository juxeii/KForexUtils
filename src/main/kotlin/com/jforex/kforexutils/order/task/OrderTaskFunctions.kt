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
) = with(taskParams.callHandlers) {
    onStart()
    ReaderApi
        .ask<KForexUtils>()
        .flatMap { extendCallableWithEventHandlerRegistration(taskParams.eventHandlerParams, orderCallable) }
        .flatMap { executeTaskOnStrategyThreadBlocking(it) }
        .map { taskTry -> taskTry.fold({ onError(it) }, { onSuccess(it) }) }
}

private fun extendCallableWithEventHandlerRegistration(
    eventHandlerParams: OrderEventHandlerParams,
    orderCallable: KCallable<IOrder>
) = ReaderApi
    .ask<KForexUtils>()
    .map {
        {
            val order = orderCallable()
            registerEventHandlerParams(order, eventHandlerParams)
            order
        }
    }