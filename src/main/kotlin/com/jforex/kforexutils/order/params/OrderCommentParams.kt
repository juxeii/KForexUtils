package com.jforex.kforexutils.order.params

import com.jforex.kforexutils.order.params.actions.OrderCommentActions

data class OrderCommentParams(
    val comment: String,
    val actions: OrderCommentActions = OrderCommentActions(),
    val retry: OrderRetryParams = OrderRetryParams()
)