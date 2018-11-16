package com.jforex.kforexutils.order.event

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.misc.EventHandlers
import com.jforex.kforexutils.misc.KRunnable

data class OrderEventsConfigurationParams(
    val order: IOrder,
    val eventHandlers: EventHandlers,
    val finishEventTypes: Set<OrderEventType>,
    val completionCall: KRunnable
)