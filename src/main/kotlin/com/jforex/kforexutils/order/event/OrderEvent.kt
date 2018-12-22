package com.jforex.kforexutils.order.event

import com.dukascopy.api.IOrder

data class OrderEvent(
    val order: IOrder,
    val type: OrderEventType
)
