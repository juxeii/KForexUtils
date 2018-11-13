package com.jforex.kforexutils.order.params

data class OrderAmountParams(
    val amount: Double,
    val actions: OrderAmountActions = OrderAmountActions()
)