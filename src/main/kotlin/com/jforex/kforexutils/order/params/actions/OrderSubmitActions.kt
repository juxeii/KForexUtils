package com.jforex.kforexutils.order.params.actions

import com.jforex.kforexutils.misc.OrderEventConsumer
import com.jforex.kforexutils.misc.emptyOrderEventConsumer

data class OrderSubmitActions(
    val basicActions: OrderTaskActions = OrderTaskActions(),
    val onSubmit: OrderEventConsumer = emptyOrderEventConsumer,
    val onPartialFill: OrderEventConsumer = emptyOrderEventConsumer,
    val onFullFill: OrderEventConsumer = emptyOrderEventConsumer
)