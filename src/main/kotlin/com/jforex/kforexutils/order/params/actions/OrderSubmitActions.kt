package com.jforex.kforexutils.order.params.actions

import com.jforex.kforexutils.misc.OrderEventConsumer
import com.jforex.kforexutils.misc.RejectOrderEventConsumer
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.misc.emptyRejectOrderEventConsumer

data class OrderSubmitActions(
    val basicActions: OrderBasicActions = OrderBasicActions(),
    val onSubmit: OrderEventConsumer = emptyOrderEventConsumer,
    val onPartialFill: OrderEventConsumer = emptyOrderEventConsumer,
    val onFullFill: OrderEventConsumer = emptyOrderEventConsumer,
    val onSubmitReject: OrderEventConsumer = emptyOrderEventConsumer,
    val onFillReject: RejectOrderEventConsumer = emptyRejectOrderEventConsumer
)