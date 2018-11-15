package com.jforex.kforexutils.order

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.misc.KRunnable
import com.jforex.kforexutils.order.extension.kForexUtils
import com.jforex.kforexutils.order.task.OrderTaskParams
import com.jforex.kforexutils.order.task.runOrderTask

internal fun changeOrder(
    order: IOrder,
    changeCall: KRunnable,
    taskParams: OrderTaskParams
) = runOrderTask(
    orderCallable = changeToCallableCall(order, changeCall),
    taskParams = taskParams
).run(order.kForexUtils)

private fun changeToCallableCall(
    order: IOrder,
    changeCall: KRunnable
) = {
    changeCall()
    order
}