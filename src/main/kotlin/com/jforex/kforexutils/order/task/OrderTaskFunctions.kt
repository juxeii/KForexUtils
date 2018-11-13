package com.jforex.kforexutils.order.task

import arrow.data.ReaderApi
import arrow.data.map
import com.dukascopy.api.IContext
import com.dukascopy.api.IOrder
import com.jforex.kforexutils.context.executeTaskOnStrategyThreadBlocking
import com.jforex.kforexutils.misc.KCallable

internal fun runOrderTask(
    orderCall: KCallable<IOrder>,
    taskData: OrderCallActions
) = with(taskData) {
    onStart()
    ReaderApi
        .ask<IContext>()
        .map { context ->
            context
                .executeTaskOnStrategyThreadBlocking(orderCall)
                .fold({ onError(it) }, { onSuccess(it) })
        }
}