package com.jforex.kforexutils.misc

import com.jforex.kforexutils.order.task.OrderCallActions

@DslMarker
annotation class OrderDsl

internal val emptyAction: KRunnable = { }
internal val emptyOrderConsumer: OrderConsumer = {}
internal val emptyErrorConsumer: ErrorConsumer = { }
internal val emptyOrderEventConsumer: OrderEventConsumer = {}
internal val emptyCallActions = OrderCallActions()
