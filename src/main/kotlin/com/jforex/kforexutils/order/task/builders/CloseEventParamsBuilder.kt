package com.jforex.kforexutils.order.task.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.event.handler.data.CloseEventData
import com.jforex.kforexutils.order.task.OrderEventHandlerParams

@OrderDsl
class CloseEventParamsBuilder : IParamsBuilder<CloseEventParamsBuilder>
{
    var onClose = emptyOrderEventConsumer
    var onPartialClose = emptyOrderEventConsumer
    var onCloseReject = emptyOrderEventConsumer

    private fun getEventHandlerParams() = OrderEventHandlerParams(
        eventData = CloseEventData(),
        eventHandlers = mapOf(
            OrderEventType.CLOSE_OK to onClose,
            OrderEventType.PARTIAL_CLOSE_OK to onPartialClose,
            OrderEventType.CLOSE_REJECTED to onCloseReject
        )
    )

    override fun build(block: CloseEventParamsBuilder.() -> Unit) = apply(block).getEventHandlerParams()
}