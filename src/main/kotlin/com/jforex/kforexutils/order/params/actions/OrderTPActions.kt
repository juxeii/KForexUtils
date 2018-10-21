package com.jforex.kforexutils.order.params.actions

import com.jforex.kforexutils.misc.OrderEventConsumer
import com.jforex.kforexutils.misc.RejectOrderEventConsumer
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.misc.emptyRejectOrderEventConsumer

data class OrderTPActions(
    val basicActions: OrderBasicActions = OrderBasicActions(),
    val onTPChange: OrderEventConsumer = emptyOrderEventConsumer,
    val onReject: RejectOrderEventConsumer = emptyRejectOrderEventConsumer
)