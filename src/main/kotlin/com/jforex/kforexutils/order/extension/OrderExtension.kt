package com.jforex.kforexutils.order.extension

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.misc.FieldProperty
import com.jforex.kforexutils.misc.KRunnable
import com.jforex.kforexutils.order.event.OrderEventHandler
import com.jforex.kforexutils.order.event.handler.OrderTaskRunner
import com.jforex.kforexutils.order.event.handler.data.OrderEventHandlerData

internal var IOrder.taskRunner: OrderTaskRunner by FieldProperty<IOrder, OrderTaskRunner>()
internal var IOrder.eventHandler: OrderEventHandler by FieldProperty<IOrder, OrderEventHandler>()

internal fun IOrder.runTask(
    orderCall: KRunnable,
    handlerData: OrderEventHandlerData
) {
    val orderCallWithEventHandlerInitialization = {
        orderCall()
        eventHandler.observable()
    }
    taskRunner.run(orderCallWithEventHandlerInitialization, handlerData)
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