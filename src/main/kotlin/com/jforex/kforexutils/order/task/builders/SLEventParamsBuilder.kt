package com.jforex.kforexutils.order.task.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.event.handler.data.ChangeEventData
import com.jforex.kforexutils.order.task.OrderEventHandlerParams

@OrderDsl
class SLEventParamsBuilder : IParamsBuilder<SLEventParamsBuilder>
{
    var onSLChange = emptyOrderEventConsumer
    var onSLReject = emptyOrderEventConsumer

    private fun getEventHandlerParams() = OrderEventHandlerParams(
        eventData = ChangeEventData(OrderEventType.CHANGED_SL),
        eventHandlers = mapOf(
            OrderEventType.CHANGED_SL to onSLChange,
            OrderEventType.CHANGE_REJECTED to onSLReject
        )
    )

    override fun build(block: SLEventParamsBuilder.() -> Unit) = apply(block).getEventHandlerParams()
}