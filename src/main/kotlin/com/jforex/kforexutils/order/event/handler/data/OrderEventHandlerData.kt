package com.jforex.kforexutils.order.event.handler.data

import com.jforex.kforexutils.misc.KRunnable
import com.jforex.kforexutils.misc.OrderEventConsumer
import com.jforex.kforexutils.order.event.OrderEvent
import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.event.handler.OrderEventHandlerType
import com.jforex.kforexutils.order.task.OrderTaskActions

interface OrderEventHandlerData
{
    val eventHandlers: Map<OrderEventType, OrderEventConsumer>

    val finishEventTypes: Set<OrderEventType>

    val rejectEventType: OrderEventType

    var retryCall: KRunnable

    val taskActions: OrderTaskActions

    val type: OrderEventHandlerType

    fun onRejectEvent(orderEvent: OrderEvent) = taskActions.taskRetry?.onRejectEvent(orderEvent, retryCall)
}