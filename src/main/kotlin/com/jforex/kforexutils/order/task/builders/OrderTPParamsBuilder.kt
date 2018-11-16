package com.jforex.kforexutils.order.task.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.misc.emptyCallActions
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.event.handler.data.ChangeEventData
import com.jforex.kforexutils.order.task.OrderEventHandlerParams
import com.jforex.kforexutils.order.task.OrderTaskParams

@OrderDsl
class OrderTPParamsBuilder
{
    private var callActions = emptyCallActions
    var onTPChange = emptyOrderEventConsumer
    var onTPReject = emptyOrderEventConsumer

    fun callActions(block: OrderCallActionsBuilder.() -> Unit)
    {
        callActions = OrderCallActionsBuilder(block)
    }

    private fun build() = OrderTaskParams(
        callActions = callActions,
        eventHandlerParams = OrderEventHandlerParams(
            eventData = ChangeEventData(OrderEventType.CHANGED_TP),
            eventHandlers = mapOf(
                OrderEventType.CHANGED_TP to onTPChange,
                OrderEventType.CHANGE_REJECTED to onTPReject
            )
        )
    )

    companion object
    {
        operator fun invoke(block: OrderTPParamsBuilder.() -> Unit) =
            OrderTPParamsBuilder()
                .apply(block)
                .build()
    }
}