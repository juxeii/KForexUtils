package com.jforex.kforexutils.order.task.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.event.handler.data.ChangeEventData
import com.jforex.kforexutils.order.task.OrderEventHandlerParams

@OrderDsl
class LabelEventParamsBuilder : IParamsBuilder<LabelEventParamsBuilder>
{
    var onLabelChange = emptyOrderEventConsumer
    var onLabelReject = emptyOrderEventConsumer

    private fun getEventHandlerParams() = OrderEventHandlerParams(
        eventData = ChangeEventData(OrderEventType.CHANGED_LABEL),
        eventHandlers = filterEventHandlers(createMap())
    )

    private fun createMap() = mapOf(
        OrderEventType.CHANGED_LABEL to onLabelChange,
        OrderEventType.CHANGE_REJECTED to onLabelReject
    )

    override fun build(block: LabelEventParamsBuilder.() -> Unit) = apply(block).getEventHandlerParams()
}