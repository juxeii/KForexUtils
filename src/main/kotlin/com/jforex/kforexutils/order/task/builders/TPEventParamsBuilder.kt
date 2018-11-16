package com.jforex.kforexutils.order.task.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.event.handler.data.ChangeEventData
import com.jforex.kforexutils.order.task.OrderEventHandlerParams

@OrderDsl
class TPEventParamsBuilder : IParamsBuilder<TPEventParamsBuilder>
{
    var onTPChange = emptyOrderEventConsumer
    var onTPReject = emptyOrderEventConsumer

    private fun getEventHandlerParams() = OrderEventHandlerParams(
        eventData = ChangeEventData(OrderEventType.CHANGED_TP),
        eventHandlers = mapOf(
            OrderEventType.CHANGED_TP to onTPChange,
            OrderEventType.CHANGE_REJECTED to onTPReject
        )
    )

    override fun build(block: TPEventParamsBuilder.() -> Unit) = apply(block).getEventHandlerParams()
}