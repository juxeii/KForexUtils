package com.jforex.kforexutils.order.params.actions

import com.jforex.kforexutils.misc.OrderEventConsumer
import com.jforex.kforexutils.misc.emptyOrderEventConsumer

data class OrderSubmitActions(
    val basicActions: OrderBasicActions = OrderBasicActions(),
    val onSubmit: OrderEventConsumer = emptyOrderEventConsumer,
    val onPartialFill: OrderEventConsumer = emptyOrderEventConsumer,
    val onFullFill: OrderEventConsumer = emptyOrderEventConsumer,
    val onSubmitReject: OrderEventConsumer = emptyOrderEventConsumer,
    val onFillReject: OrderEventConsumer = emptyOrderEventConsumer
)