package com.jforex.kforexutils.misc

import com.jforex.kforexutils.order.event.OrderEvent
import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.task.OrderCallActions
import com.jforex.kforexutils.order.task.OrderCallParams
import com.jforex.kforexutils.order.task.TaskRetry


@DslMarker
annotation class OrderDsl

val emptyAction: KRunnable = { }
val emptyOrderConsumer: OrderConsumer = {}
val emptyErrorConsumer: ErrorConsumer = { }
val emptyOrderEventConsumer: OrderEventConsumer = {}
val emptyOrderEventHandlers = emptyMap<OrderEventType, OrderEventConsumer>()
val emptyCallActions = OrderCallActions()
val emptyTaskRetryHandler = DefaultTaskRetry()
val emptyOrderCallParams = OrderCallParams()

fun thisThreadName(): String = Thread
    .currentThread()
    .name

class DefaultTaskRetry : TaskRetry {
    override fun onRejectEvent(rejectEvent: OrderEvent, retryCall: KRunnable) {}
}

