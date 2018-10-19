package com.jforex.kforexutils.order.params

import com.jforex.kforexutils.order.params.actions.OrderTPActions

data class OrderTPParams(
    val price: Double,
    val actions: OrderTPActions = OrderTPActions(),
    val retry: OrderRetryParams = OrderRetryParams()
)