package com.jforex.kforexutils.order.params.actions

import com.jforex.kforexutils.misc.OrderEventConsumer
import com.jforex.kforexutils.misc.emptyOrderEventConsumer

data class OrderGTTActions(
    val basicActions: OrderTaskActions = OrderTaskActions(),
    val onGTTChange: OrderEventConsumer = emptyOrderEventConsumer
)