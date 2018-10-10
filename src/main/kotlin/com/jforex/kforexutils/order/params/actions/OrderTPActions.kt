package com.jforex.kforexutils.order.params.actions

import com.jforex.kforexutils.misc.OrderEventConsumer
import com.jforex.kforexutils.misc.emptyOrderEventConsumer

data class OrderTPActions @JvmOverloads constructor(
    val basicActions: OrderBasicActions = OrderBasicActions(),
    val onTPChange: OrderEventConsumer = emptyOrderEventConsumer,
    val onReject: OrderEventConsumer = emptyOrderEventConsumer
)