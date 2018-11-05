package com.jforex.kforexutils.order.extension

import arrow.core.Failure
import arrow.core.Success
import com.dukascopy.api.IContext
import com.dukascopy.api.IOrder
import com.jforex.kforexutils.context.executeTaskOnStrategyThreadBlocking
import com.jforex.kforexutils.misc.FieldProperty
import com.jforex.kforexutils.misc.KRunnable
import com.jforex.kforexutils.order.event.handler.OrderEventHandler
import com.jforex.kforexutils.order.event.handler.data.OrderEventHandlerData

internal var IOrder.eventHandler: OrderEventHandler by FieldProperty()
internal var IOrder.context: IContext by FieldProperty()

internal fun IOrder.runTask(
    orderCall: KRunnable,
    handlerData: OrderEventHandlerData
)
{
    handlerData.retryCall = { runTask(orderCall, handlerData) }
    val orderCallWithEventHandlerInitialization = {
        orderCall()
        eventHandler.register(handlerData)
        this
    }

    with(handlerData.taskActions) {
        onStart()
        when (val taskResult = context.executeTaskOnStrategyThreadBlocking(orderCallWithEventHandlerInitialization))
        {
            is Success -> onSuccess(taskResult.value)
            is Failure -> onError(taskResult.exception)
        }
    }
}

val IOrder.isOpened
    get() = state == IOrder.State.OPENED
val IOrder.isFilled
    get() = state == IOrder.State.FILLED
val IOrder.isClosed
    get() = state == IOrder.State.CLOSED
val IOrder.isCanceled
    get() = state == IOrder.State.CANCELED
val IOrder.isConditional
    get() = orderCommand.isConditional