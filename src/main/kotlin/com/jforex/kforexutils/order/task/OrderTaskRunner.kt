package com.jforex.kforexutils.order.task

import arrow.core.Failure
import arrow.core.Success
import com.dukascopy.api.IContext
import com.dukascopy.api.IOrder
import com.jforex.kforexutils.context.executeTaskOnStrategyThreadBlocking
import com.jforex.kforexutils.misc.KCallable

class OrderTaskRunner(val context: IContext)

internal fun runOrderTask(
    task: KCallable<IOrder>,
    actions: OrderTaskActions,
    context: IContext
) = with(actions) {
    onStart()
    val taskResult = context.executeTaskOnStrategyThreadBlocking(task)
    when (taskResult) {
        is Success -> onSuccess(taskResult.value)
        is Failure -> onError(taskResult.exception)
    }
}