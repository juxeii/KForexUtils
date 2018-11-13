package com.jforex.kforexutils.order.params

import com.jforex.kforexutils.order.params.actions.OrderTaskParams

data class OrderTPParams(
    val tpPrice: Double,
    val taskParams: OrderTaskParams
)