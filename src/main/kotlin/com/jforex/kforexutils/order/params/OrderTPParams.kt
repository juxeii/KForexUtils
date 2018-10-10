package com.jforex.kforexutils.order.params

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.order.params.actions.OrderTPActions

data class OrderTPParams(
    val order: IOrder,
    val price: Double,
    val tpActions: OrderTPActions = OrderTPActions()
)