package com.jforex.kforexutils.order.params

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.order.params.actions.OrderMergeActions

data class OrderMergeParams(
    val label: String,
    val orders: Collection<IOrder>,
    val comment: String = "",
    val actions: OrderMergeActions = OrderMergeActions()
)