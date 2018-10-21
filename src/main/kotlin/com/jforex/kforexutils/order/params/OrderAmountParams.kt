package com.jforex.kforexutils.order.params

import com.jforex.kforexutils.order.params.actions.OrderAmountActions

data class OrderAmountParams(
    val amount: Double,
    val actions: OrderAmountActions = OrderAmountActions()
)