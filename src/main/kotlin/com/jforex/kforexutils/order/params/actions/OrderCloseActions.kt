package com.jforex.kforexutils.order.params.actions

import com.jforex.kforexutils.misc.OrderEventConsumer
import com.jforex.kforexutils.misc.emptyOrderEventConsumer

data class OrderCloseActions(
    val basicActions: OrderBasicActions = OrderBasicActions(),
    val onClose: OrderEventConsumer = emptyOrderEventConsumer,
    val onPartialClose: OrderEventConsumer = emptyOrderEventConsumer,
    val onReject: OrderEventConsumer = emptyOrderEventConsumer
)