package com.jforex.kforexutils.order.params.actions

import com.jforex.kforexutils.misc.OrderEventConsumer
import com.jforex.kforexutils.misc.emptyOrderEventConsumer

data class OrderSLActions @JvmOverloads constructor(
    val basicActions: OrderBasicActions = OrderBasicActions(),
    val onSLChange: OrderEventConsumer = emptyOrderEventConsumer,
    val onReject: OrderEventConsumer = emptyOrderEventConsumer
)