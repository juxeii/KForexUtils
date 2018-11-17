package com.jforex.kforexutils.order.extension

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.order.changeToCallableCall
import com.jforex.kforexutils.order.task.builders.CommentEventParamsBuilder
import com.jforex.kforexutils.order.task.builders.OrderParamsBuilder
import com.jforex.kforexutils.order.task.runOrderTask

fun IOrder.setComment(
    comment: String,
    block: OrderParamsBuilder<CommentEventParamsBuilder>.() -> Unit = {}
)
{
    runOrderTask(
        orderCallable = changeToCallableCall(this) { setComment(comment) },
        taskParams = OrderParamsBuilder(CommentEventParamsBuilder(), block)
    ).run(kForexUtils)
}