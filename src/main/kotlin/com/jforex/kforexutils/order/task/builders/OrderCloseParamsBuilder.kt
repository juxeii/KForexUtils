package com.jforex.kforexutils.order.task.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.misc.emptyCallActions
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.event.handler.data.CloseEventData
import com.jforex.kforexutils.order.task.OrderTaskEventParams
import com.jforex.kforexutils.order.task.OrderTaskParams

@OrderDsl
class OrderCloseParamsBuilder
{
    private var callActions = emptyCallActions
    var onClose = emptyOrderEventConsumer
    var onPartialClose = emptyOrderEventConsumer
    var onCloseReject = emptyOrderEventConsumer

    private val eventHandlers = mapOf(
        OrderEventType.CLOSE_OK to onClose,
        OrderEventType.PARTIAL_CLOSE_OK to onPartialClose,
        OrderEventType.CLOSE_REJECTED to onCloseReject
    )

    fun callActions(block: OrderCallActionsBuilder.() -> Unit)
    {
        callActions = OrderCallActionsBuilder(block)
    }

    private fun build() = OrderTaskParams(
        callActions = callActions,
        eventParams = OrderTaskEventParams(
            eventData = CloseEventData(),
            eventHandlers = eventHandlers
        )
    )

    companion object
    {
        operator fun invoke(block: OrderCloseParamsBuilder.() -> Unit) =
            OrderCloseParamsBuilder()
                .apply(block)
                .build()
    }
}