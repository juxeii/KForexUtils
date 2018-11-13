package com.jforex.kforexutils.order.extension

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.order.task.builders.OrderCommentParamsBuilder

fun IOrder.setComment(
    comment: String,
    block: OrderCommentParamsBuilder.() -> Unit = {}
) = runTask(
    orderCall = { setComment(comment) },
    taskParams = OrderCommentParamsBuilder(block)
)