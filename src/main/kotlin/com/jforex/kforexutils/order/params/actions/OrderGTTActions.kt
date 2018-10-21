package com.jforex.kforexutils.order.params.actions

import com.jforex.kforexutils.misc.OrderEventConsumer
import com.jforex.kforexutils.misc.RejectOrderEventConsumer
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.misc.emptyRejectOrderEventConsumer

data class OrderGTTActions(
    val basicActions: OrderBasicActions = OrderBasicActions(),
    val onGTTChange: OrderEventConsumer = emptyOrderEventConsumer,
    val onReject: RejectOrderEventConsumer = emptyRejectOrderEventConsumer
)