package com.jforex.kforexutils.order.task.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.misc.emptyCallActions
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.event.handler.data.ChangeEventData
import com.jforex.kforexutils.order.task.OrderEventHandlerParams
import com.jforex.kforexutils.order.task.OrderTaskParams

@OrderDsl
class OrderAmountParamsBuilder
{
    private var callActions = emptyCallActions
    var onAmountChange = emptyOrderEventConsumer
    var onAmountReject = emptyOrderEventConsumer

    fun callActions(block: OrderCallActionsBuilder.() -> Unit)
    {
        callActions = OrderCallActionsBuilder(block)
    }

    private fun build() = OrderTaskParams(
        callActions = callActions,
        eventHandlerParams = OrderEventHandlerParams(
            eventData = ChangeEventData(OrderEventType.CHANGED_AMOUNT),
            eventHandlers = mapOf(
                OrderEventType.CHANGED_PRICE to onAmountChange,
                OrderEventType.CHANGE_REJECTED to onAmountReject
            )
        )
    )

    companion object
    {
        operator fun invoke(block: OrderAmountParamsBuilder.() -> Unit) =
            OrderAmountParamsBuilder()
                .apply(block)
                .build()
    }
}