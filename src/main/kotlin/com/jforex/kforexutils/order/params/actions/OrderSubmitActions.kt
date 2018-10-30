package com.jforex.kforexutils.order.params.actions

import com.jforex.kforexutils.misc.OrderEventConsumer
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.order.task.OrderTaskActions

data class OrderSubmitActions(
    val taskActions: OrderTaskActions = OrderTaskActions(),
    val onSubmit: OrderEventConsumer = emptyOrderEventConsumer,
    val onPartialFill: OrderEventConsumer = emptyOrderEventConsumer,
    val onFullFill: OrderEventConsumer = emptyOrderEventConsumer
)