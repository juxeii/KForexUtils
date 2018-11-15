package com.jforex.kforexutils.misc

import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.task.OrderCallActions

@DslMarker
annotation class OrderDsl

val emptyAction: KRunnable = { }
val emptyOrderConsumer: OrderConsumer = {}
val emptyErrorConsumer: ErrorConsumer = { }
val emptyOrderEventConsumer: OrderEventConsumer = {}
val emptyOrderEventHandlers = emptyMap<OrderEventType, OrderEventConsumer>()
val emptyCallActions = OrderCallActions()
