package com.jforex.kforexutils.order.event.handler.data

import com.jforex.kforexutils.misc.KRunnable
import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.event.handler.OrderEventHandlerType
import com.jforex.kforexutils.order.params.actions.OrderOpenPriceActions

data class SetOpenPriceEventHandlerData(
    private val actions: OrderOpenPriceActions,
    override val retryCall: KRunnable
) : OrderEventHandlerData {
    override val eventHandlers = mapOf(
        OrderEventType.CHANGED_PRICE to actions.onOpenPriceChange
    )
    override val finishEventTypes = setOf(
        OrderEventType.CHANGED_PRICE,
        OrderEventType.CHANGE_REJECTED
    )
    override val rejectEventType = OrderEventType.CHANGE_REJECTED
    override val taskActions = actions.taskActions
    override val type = OrderEventHandlerType.CHANGE
}