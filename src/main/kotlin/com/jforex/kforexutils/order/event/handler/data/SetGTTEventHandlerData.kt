package com.jforex.kforexutils.order.event.handler.data

import com.jforex.kforexutils.misc.KRunnable
import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.event.handler.OrderEventHandlerType
import com.jforex.kforexutils.order.params.actions.OrderGTTActions

data class SetGTTEventHandlerData(private val actions: OrderGTTActions) : OrderEventHandlerData
{
    override val eventHandlers = mapOf(
        OrderEventType.CHANGED_GTT to actions.onGTTChange
    )
    override val finishEventTypes = setOf(
        OrderEventType.CHANGED_GTT,
        OrderEventType.CHANGE_REJECTED
    )
    override val rejectEventType = OrderEventType.CHANGE_REJECTED
    override var retryCall: KRunnable = {}
    override val taskActions = actions.taskActions
    override val type = OrderEventHandlerType.CHANGE
}