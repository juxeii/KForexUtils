package com.jforex.kforexutils.order.params.actions

import com.jforex.kforexutils.misc.OrderEventConsumer
import com.jforex.kforexutils.misc.emptyOrderEventConsumer

data class OrderAmountActions(
    val basicActions: OrderTaskActions = OrderTaskActions(),
    val onAmountChange: OrderEventConsumer = emptyOrderEventConsumer
)