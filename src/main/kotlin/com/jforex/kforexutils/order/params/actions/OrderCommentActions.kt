package com.jforex.kforexutils.order.params.actions

import com.jforex.kforexutils.misc.OrderEventConsumer
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.order.task.OrderTaskActions

data class OrderCommentActions(
    val taskActions: OrderTaskActions = OrderTaskActions(),
    val onCommentChange: OrderEventConsumer = emptyOrderEventConsumer
)