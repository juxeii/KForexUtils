package com.jforex.kforexutils.order.params.actions

import com.jforex.kforexutils.misc.OrderEventConsumer
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.order.task.OrderTaskData

data class OrderSubmitActions(
    val taskData: OrderTaskData = OrderTaskData(),
    val onSubmit: OrderEventConsumer = emptyOrderEventConsumer,
    val onPartialFill: OrderEventConsumer = emptyOrderEventConsumer,
    val onFullFill: OrderEventConsumer = emptyOrderEventConsumer
)