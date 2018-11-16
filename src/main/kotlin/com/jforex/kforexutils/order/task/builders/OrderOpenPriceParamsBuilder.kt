package com.jforex.kforexutils.order.task.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.misc.emptyCallActions
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.event.handler.data.ChangeEventData
import com.jforex.kforexutils.order.task.OrderEventHandlerParams
import com.jforex.kforexutils.order.task.OrderTaskParams

@OrderDsl
class OrderOpenPriceParamsBuilder
{
    private var callActions = emptyCallActions
    var onOpenPriceChange = emptyOrderEventConsumer
    var onOpenPriceReject = emptyOrderEventConsumer

    fun callActions(block: OrderCallActionsBuilder.() -> Unit)
    {
        callActions = OrderCallActionsBuilder(block)
    }

    private fun build() = OrderTaskParams(
        callActions = callActions,
        eventHandlerParams = OrderEventHandlerParams(
            eventData = ChangeEventData(OrderEventType.CHANGED_PRICE),
            eventHandlers = mapOf(
                OrderEventType.CHANGED_PRICE to onOpenPriceChange,
                OrderEventType.CHANGE_REJECTED to onOpenPriceReject
            )
        )
    )

    companion object
    {
        operator fun invoke(block: OrderOpenPriceParamsBuilder.() -> Unit) =
            OrderOpenPriceParamsBuilder()
                .apply(block)
                .build()
    }
}