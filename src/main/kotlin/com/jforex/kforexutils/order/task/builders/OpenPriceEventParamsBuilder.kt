package com.jforex.kforexutils.order.task.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.event.handler.data.ChangeEventData
import com.jforex.kforexutils.order.task.OrderEventHandlerParams

@OrderDsl
class OpenPriceEventParamsBuilder : IParamsBuilder<OpenPriceEventParamsBuilder>
{
    var onOpenPriceChange = emptyOrderEventConsumer
    var onOpenPriceReject = emptyOrderEventConsumer

    private fun getEventHandlerParams() = OrderEventHandlerParams(
        eventData = ChangeEventData(OrderEventType.CHANGED_PRICE),
        eventHandlers = mapOf(
            OrderEventType.CHANGED_PRICE to onOpenPriceChange,
            OrderEventType.CHANGE_REJECTED to onOpenPriceReject
        )
    )

    override fun build(block: OpenPriceEventParamsBuilder.() -> Unit) = apply(block).getEventHandlerParams()
}