package com.jforex.kforexutils.order.extension

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.order.event.handler.data.SetCommentEventData
import com.jforex.kforexutils.order.params.builders.OrderCommentParamsBuilder

fun IOrder.setComment(comment: String, block: OrderCommentParamsBuilder.() -> Unit = {}) {
    val params = OrderCommentParamsBuilder(comment, block)
    val retryCall = { setComment(comment, block) }
    runTask(
        orderCall = { setComment(params.comment) },
        data = SetCommentEventData(params.actions, retryCall)
    )
}