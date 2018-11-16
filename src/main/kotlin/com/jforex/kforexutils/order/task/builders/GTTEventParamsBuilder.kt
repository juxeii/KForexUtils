package com.jforex.kforexutils.order.task.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.event.handler.data.ChangeEventData
import com.jforex.kforexutils.order.task.OrderEventHandlerParams

@OrderDsl
class GTTEventParamsBuilder : IParamsBuilder<GTTEventParamsBuilder>
{
    var onGTTChange = emptyOrderEventConsumer
    var onGTTReject = emptyOrderEventConsumer

    private fun getEventHandlerParams() = OrderEventHandlerParams(
        eventData = ChangeEventData(OrderEventType.CHANGED_GTT),
        eventHandlers = mapOf(
            OrderEventType.CHANGED_GTT to onGTTChange,
            OrderEventType.CHANGE_REJECTED to onGTTReject
        )
    )

    override fun build(block: GTTEventParamsBuilder.() -> Unit) = apply(block).getEventHandlerParams()
}