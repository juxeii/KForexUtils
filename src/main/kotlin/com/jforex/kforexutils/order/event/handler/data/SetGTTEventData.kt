package com.jforex.kforexutils.order.event.handler.data

import com.jforex.kforexutils.misc.KRunnable
import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.event.handler.OrderEventHandlerType
import com.jforex.kforexutils.order.params.actions.OrderGTTActions

data class SetGTTEventData(
    private val actions: OrderGTTActions,
    override val retryCall: KRunnable
) : OrderEventData {
    override val eventHandlers = mapOf(
        OrderEventType.CHANGED_GTT to actions.onGTTChange
    )
    override val finishEventTypes = setOf(
        OrderEventType.CHANGED_GTT,
        OrderEventType.CHANGE_REJECTED
    )
    override val rejectEventType = OrderEventType.CHANGE_REJECTED
    override val taskData = actions.taskData
    override val type = OrderEventHandlerType.CHANGE
}