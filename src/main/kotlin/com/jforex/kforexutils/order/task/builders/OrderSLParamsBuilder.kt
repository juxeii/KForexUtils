package com.jforex.kforexutils.order.task.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.misc.emptyCallActions
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.event.handler.data.ChangeEventData
import com.jforex.kforexutils.order.task.OrderEventHandlerParams
import com.jforex.kforexutils.order.task.OrderTaskParams

@OrderDsl
class OrderSLParamsBuilder
{
    private var callActions = emptyCallActions
    var onSLChange = emptyOrderEventConsumer
    var onSLReject = emptyOrderEventConsumer

    fun callActions(block: OrderCallActionsBuilder.() -> Unit)
    {
        callActions = OrderCallActionsBuilder(block)
    }

    private fun build() = OrderTaskParams(
        callActions = callActions,
        eventHandlerParams = OrderEventHandlerParams(
            eventData = ChangeEventData(OrderEventType.CHANGED_SL),
            eventHandlers = mapOf(
                OrderEventType.CHANGED_SL to onSLChange,
                OrderEventType.CHANGE_REJECTED to onSLReject
            )
        )
    )

    companion object
    {
        operator fun invoke(block: OrderSLParamsBuilder.() -> Unit) =
            OrderSLParamsBuilder()
                .apply(block)
                .build()
    }
}
