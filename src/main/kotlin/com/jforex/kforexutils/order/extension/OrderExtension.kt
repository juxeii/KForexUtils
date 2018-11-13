package com.jforex.kforexutils.order.extension

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.misc.FieldProperty
import com.jforex.kforexutils.misc.KForexUtils
import com.jforex.kforexutils.misc.KRunnable
import com.jforex.kforexutils.order.event.handler.OrderEventManager
import com.jforex.kforexutils.order.task.OrderTaskExecutionParams
import com.jforex.kforexutils.order.task.OrderTaskParams
import com.jforex.kforexutils.order.task.runOrderTask

internal var IOrder.kForexUtils: KForexUtils by FieldProperty()
internal var IOrder.eventManager: OrderEventManager by FieldProperty()

internal fun IOrder.runTask(
    orderCall: KRunnable,
    taskParams: OrderTaskParams
) {
    val retryCall = { runTask(orderCall, taskParams) }
    val executionParams = OrderTaskExecutionParams(
        eventParams = taskParams.eventParams,
        retryCall = retryCall,
        retryHandler = taskParams.callParams.retryHandler
    )
    val orderCallWithEventHandlerInitialization = {
        orderCall()
        eventManager.registerHandler(executionParams)
        this
    }
    runOrderTask(orderCallWithEventHandlerInitialization, taskParams.callParams.callActions).run(kForexUtils.context)
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