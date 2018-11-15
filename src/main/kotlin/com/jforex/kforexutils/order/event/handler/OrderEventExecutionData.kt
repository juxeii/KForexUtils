package com.jforex.kforexutils.order.event.handler

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.order.task.OrderTaskExecutionParams

data class OrderEventExecutionData(
    val order: IOrder,
    val params: OrderTaskExecutionParams
)
