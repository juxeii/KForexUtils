package com.jforex.kforexutils.order.params.actions

import com.jforex.kforexutils.misc.OrderEventConsumer
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.order.task.OrderTaskData

data class OrderSLActions(
    val taskData: OrderTaskData = OrderTaskData(),
    val onSLChange: OrderEventConsumer = emptyOrderEventConsumer
)