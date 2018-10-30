package com.jforex.kforexutils.order.params.actions

import com.jforex.kforexutils.misc.OrderEventConsumer
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.order.task.OrderTaskActions

data class OrderCloseActions(
    val taskActions: OrderTaskActions = OrderTaskActions(),
    val onClose: OrderEventConsumer = emptyOrderEventConsumer,
    val onPartialClose: OrderEventConsumer = emptyOrderEventConsumer
)