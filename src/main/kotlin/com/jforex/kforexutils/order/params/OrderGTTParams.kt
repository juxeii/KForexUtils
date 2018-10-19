package com.jforex.kforexutils.order.params

import com.jforex.kforexutils.order.params.actions.OrderGTTActions

data class OrderGTTParams(
    val gtt: Long,
    val actions: OrderGTTActions = OrderGTTActions(),
    val retry: OrderRetryParams = OrderRetryParams()
)