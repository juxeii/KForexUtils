package com.jforex.kforexutils.order.task

import arrow.data.Reader
import arrow.data.ReaderApi
import arrow.data.map
import com.dukascopy.api.IContext
import com.dukascopy.api.IOrder
import com.jforex.kforexutils.context.executeTaskOnStrategyThreadBlocking
import com.jforex.kforexutils.misc.KCallable
import com.jforex.kforexutils.order.event.handler.data.OrderEventHandlerData

internal fun runOrderTask(
    orderCall: KCallable<IOrder>,
    handlerData: OrderEventHandlerData
): Reader<IContext, Unit> = with(handlerData.taskActions) {
    handlerData.retryCall = { runOrderTask(orderCall, handlerData) }
    onStart()
    ReaderApi
        .ask<IContext>()
        .map { context ->
            context
                .executeTaskOnStrategyThreadBlocking(orderCall)
                .fold({ onError(it) }, { onSuccess(it) })
        }
}