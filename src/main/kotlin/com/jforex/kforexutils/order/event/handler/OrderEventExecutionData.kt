package com.jforex.kforexutils.order.event.handler

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.order.task.OrderTaskEventParams

data class OrderEventExecutionData(
    val order: IOrder,
    val eventParams: OrderTaskEventParams
)
