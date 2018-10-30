package com.jforex.kforexutils.order.extension

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.misc.FieldProperty
import com.jforex.kforexutils.misc.KRunnable
import com.jforex.kforexutils.order.event.handler.OrderEventHandler
import com.jforex.kforexutils.order.event.handler.data.OrderEventHandlerData
import com.jforex.kforexutils.order.task.OrderTaskRunner

internal var IOrder.taskRunner: OrderTaskRunner by FieldProperty()
internal var IOrder.eventHandler: OrderEventHandler by FieldProperty()

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

    taskRunner.run(
        task = orderCallWithEventHandlerInitialization,
        actions = handlerData.taskActions
    )
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