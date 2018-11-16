package com.jforex.kforexutils.order.task.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.event.handler.data.SubmitEventData
import com.jforex.kforexutils.order.task.OrderEventHandlerParams

@OrderDsl
class SubmitEventParamsBuilder : IParamsBuilder<SubmitEventParamsBuilder>
{
    var onSubmit = emptyOrderEventConsumer
    var onPartialFill = emptyOrderEventConsumer
    var onFullFill = emptyOrderEventConsumer

    private fun getEventHandlerParams() = OrderEventHandlerParams(
        eventData = SubmitEventData(),
        eventHandlers = mapOf(
            OrderEventType.SUBMIT_OK to onSubmit,
            OrderEventType.PARTIALLY_FILLED to onPartialFill,
            OrderEventType.FULLY_FILLED to onFullFill
        )
    )

    override fun build(block: SubmitEventParamsBuilder.() -> Unit) = apply(block).getEventHandlerParams()
}