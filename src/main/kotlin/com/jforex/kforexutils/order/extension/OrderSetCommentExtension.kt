package com.jforex.kforexutils.order.extension

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.order.changeOrder
import com.jforex.kforexutils.order.task.builders.OrderCommentParamsBuilder

fun IOrder.setComment(
    comment: String,
    block: OrderCommentParamsBuilder.() -> Unit = {}
) = changeOrder(
    order = this,
    orderCall = { setComment(comment) },
    taskParams = OrderCommentParamsBuilder(block)
)