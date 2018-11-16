package com.jforex.kforexutils.order.extension

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.order.changeOrder
import com.jforex.kforexutils.order.task.builders.CommentEventParamsBuilder
import com.jforex.kforexutils.order.task.builders.OrderParamsBuilder

fun IOrder.setComment(
    comment: String,
    block: OrderParamsBuilder<CommentEventParamsBuilder>.() -> Unit = {}
) = changeOrder(
    order = this,
    changeCall = { setComment(comment) },
    taskParams = OrderParamsBuilder(CommentEventParamsBuilder(), block)
)