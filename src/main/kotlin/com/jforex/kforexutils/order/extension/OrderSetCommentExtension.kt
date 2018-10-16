package com.jforex.kforexutils.order.extension

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.order.event.handler.data.SetCommentEventConsumerData
import com.jforex.kforexutils.order.params.builders.OrderCommentParamsBuilder

fun IOrder.setComment(comment: String, block: OrderCommentParamsBuilder.() -> Unit = {}) {
    val params = OrderCommentParamsBuilder(comment, block)
    runTask(
        orderCall = { setComment(params.comment) },
        consumerData = SetCommentEventConsumerData(params.actions)
    )
}