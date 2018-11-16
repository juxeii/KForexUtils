package com.jforex.kforexutils.order.task.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.misc.emptyCallActions
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.event.handler.data.SubmitEventData
import com.jforex.kforexutils.order.task.OrderEventHandlerParams
import com.jforex.kforexutils.order.task.OrderTaskParams

@OrderDsl
class OrderSubmitParamsBuilder
{
    private var callActions = emptyCallActions
    var onSubmit = emptyOrderEventConsumer
    var onPartialFill = emptyOrderEventConsumer
    var onFullFill = emptyOrderEventConsumer

    fun callActions(block: OrderCallActionsBuilder.() -> Unit)
    {
        callActions = OrderCallActionsBuilder(block)
    }

    private fun build() = OrderTaskParams(
        callActions = callActions,
        eventHandlerParams = OrderEventHandlerParams(
            eventData = SubmitEventData(),
            eventHandlers = mapOf(
                OrderEventType.SUBMIT_OK to onSubmit,
                OrderEventType.PARTIALLY_FILLED to onPartialFill,
                OrderEventType.FULLY_FILLED to onFullFill
            )
        )
    )

    companion object
    {
        operator fun invoke(block: OrderSubmitParamsBuilder.() -> Unit) = OrderSubmitParamsBuilder()
            .apply(block)
            .build()
    }
}