package com.jforex.kforexutils.order.params.actions

import com.jforex.kforexutils.misc.OrderEventConsumer
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.order.task.OrderTaskActions

data class OrderOpenPriceActions(
    val taskActions: OrderTaskActions = OrderTaskActions(),
    val onOpenPriceChange: OrderEventConsumer = emptyOrderEventConsumer
)