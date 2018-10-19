package com.jforex.kforexutils.order.params

import com.jforex.kforexutils.order.params.actions.OrderLabelActions

data class OrderLabelParams(
    val label: String,
    val actions: OrderLabelActions = OrderLabelActions(),
    val retry: OrderRetryParams = OrderRetryParams()
)