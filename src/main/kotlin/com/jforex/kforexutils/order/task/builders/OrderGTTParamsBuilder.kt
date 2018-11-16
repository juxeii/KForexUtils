package com.jforex.kforexutils.order.task.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.misc.emptyCallActions
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.event.handler.data.ChangeEventData
import com.jforex.kforexutils.order.task.OrderEventHandlerParams
import com.jforex.kforexutils.order.task.OrderTaskParams

@OrderDsl
class OrderGTTParamsBuilder
{
    private var callActions = emptyCallActions
    var onGTTChange = emptyOrderEventConsumer
    var onGTTReject = emptyOrderEventConsumer

    fun callActions(block: OrderCallActionsBuilder.() -> Unit)
    {
        callActions = OrderCallActionsBuilder(block)
    }

    private fun build() = OrderTaskParams(
        callActions = callActions,
        eventHandlerParams = OrderEventHandlerParams(
            eventData = ChangeEventData(OrderEventType.CHANGED_GTT),
            eventHandlers = mapOf(
                OrderEventType.CHANGED_GTT to onGTTChange,
                OrderEventType.CHANGE_REJECTED to onGTTReject
            )
        )
    )

    companion object
    {
        operator fun invoke(block: OrderGTTParamsBuilder.() -> Unit) =
            OrderGTTParamsBuilder()
                .apply(block)
                .build()
    }
}