package com.jforex.kforexutils.order.params.actions

import com.jforex.kforexutils.misc.OrderEventConsumer
import com.jforex.kforexutils.misc.emptyOrderEventConsumer

data class OrderOpenPriceActions(
    val basicActions: OrderBasicActions = OrderBasicActions(),
    val onOpenPriceChange: OrderEventConsumer = emptyOrderEventConsumer
)