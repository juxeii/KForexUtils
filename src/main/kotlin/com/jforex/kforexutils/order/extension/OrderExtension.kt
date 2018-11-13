package com.jforex.kforexutils.order.extension

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.misc.FieldProperty
import com.jforex.kforexutils.misc.KForexUtils
import com.jforex.kforexutils.misc.KRunnable
import com.jforex.kforexutils.order.event.handler.OrderEventManager
import com.jforex.kforexutils.order.event.handler.data.OrderEventHandlerData
import com.jforex.kforexutils.order.task.runOrderTask

internal var IOrder.kForexUtils: KForexUtils by FieldProperty()
internal var IOrder.eventManager: OrderEventManager by FieldProperty()

internal fun IOrder.runTask(
    orderCall: KRunnable,
    handlerDataProvider: (KRunnable) -> OrderEventHandlerData
) {
    val handlerData = handlerDataProvider { runTask(orderCall, handlerDataProvider) }
    val orderCallWithEventHandlerInitialization = {
        orderCall()
        val filteredOrderEvents = kForexUtils
            .orderMessageGateway
            .observable
            .filter { it.order == this }
        eventManager.eventHandlers.accept(handlerData)
        this
    }
    runOrderTask(orderCallWithEventHandlerInitialization, handlerData).run(kForexUtils.context)
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