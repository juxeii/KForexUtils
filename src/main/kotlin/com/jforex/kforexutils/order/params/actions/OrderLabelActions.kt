package com.jforex.kforexutils.order.params.actions

import com.jforex.kforexutils.misc.OrderEventConsumer
import com.jforex.kforexutils.misc.emptyOrderEventConsumer

data class OrderLabelActions(
    val basicActions: OrderBasicActions = OrderBasicActions(),
    val onLabelChange: OrderEventConsumer = emptyOrderEventConsumer
)