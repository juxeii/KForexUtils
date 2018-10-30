package com.jforex.kforexutils.order.params.actions

import com.jforex.kforexutils.misc.OrderEventConsumer
import com.jforex.kforexutils.misc.emptyOrderEventConsumer

data class OrderTPActions(
    val basicActions: OrderTaskActions = OrderTaskActions(),
    val onTPChange: OrderEventConsumer = emptyOrderEventConsumer
)