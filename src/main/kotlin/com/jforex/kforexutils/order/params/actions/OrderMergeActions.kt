package com.jforex.kforexutils.order.params.actions

import com.jforex.kforexutils.misc.OrderEventConsumer
import com.jforex.kforexutils.misc.emptyOrderEventConsumer

data class OrderMergeActions(
    val basicActions: OrderTaskActions = OrderTaskActions(),
    val onMerge: OrderEventConsumer = emptyOrderEventConsumer,
    val onMergeClose: OrderEventConsumer = emptyOrderEventConsumer
)