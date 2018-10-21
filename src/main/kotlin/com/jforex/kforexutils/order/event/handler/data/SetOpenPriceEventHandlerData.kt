package com.jforex.kforexutils.order.event.handler.data

import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.event.handler.OrderEventHandlerType
import com.jforex.kforexutils.order.params.actions.OrderOpenPriceActions

data class SetOpenPriceEventHandlerData(private val actions: OrderOpenPriceActions) : OrderEventHandlerData
{
    override val eventHandlers = mapOf(
        OrderEventType.CHANGED_PRICE to actions.onOpenPriceChange
    )
    override val finishEventTypes = setOf(
        OrderEventType.CHANGED_PRICE,
        OrderEventType.CHANGE_REJECTED
    )
    override val rejectEventType = OrderEventType.CHANGE_REJECTED
    override val basicActions = actions.basicActions
    override val type = OrderEventHandlerType.CHANGE_PRICE
    override val rejectEventHandler = actions.onReject
}