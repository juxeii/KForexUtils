package com.jforex.kforexutils.order.params

import com.jforex.kforexutils.order.params.actions.OrderTaskParams

data class OrderSLParams(
    val slPrice: Double,
    val taskParams: OrderTaskParams
)