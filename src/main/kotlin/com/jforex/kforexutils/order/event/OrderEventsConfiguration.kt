package com.jforex.kforexutils.order.event

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.misc.EventHandlers
import com.jforex.kforexutils.misc.KRunnable

data class OrderEventsConfiguration(
    val order: IOrder,
    val handlers: EventHandlers,
    val finishTypes: Set<OrderEventType>,
    val completionCall: KRunnable
)