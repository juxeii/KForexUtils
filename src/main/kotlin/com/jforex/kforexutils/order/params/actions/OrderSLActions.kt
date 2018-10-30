package com.jforex.kforexutils.order.params.actions

import com.jforex.kforexutils.misc.OrderEventConsumer
import com.jforex.kforexutils.misc.emptyOrderEventConsumer

data class OrderSLActions(
    val basicActions: OrderTaskActions = OrderTaskActions(),
    val onSLChange: OrderEventConsumer = emptyOrderEventConsumer
)