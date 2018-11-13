package com.jforex.kforexutils.order.task.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.misc.emptyOrderCallParams
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.event.handler.data.SubmitEventData
import com.jforex.kforexutils.order.task.OrderTaskEventParams
import com.jforex.kforexutils.order.task.OrderTaskParams

@OrderDsl
class OrderSubmitParamsBuilder
{
    private var callParams = emptyOrderCallParams
    var onSubmit = emptyOrderEventConsumer
    var onPartialFill = emptyOrderEventConsumer
    var onFullFill = emptyOrderEventConsumer
    private var eventHandlers = mapOf(
        OrderEventType.SUBMIT_OK to onSubmit,
        OrderEventType.PARTIALLY_FILLED to onPartialFill,
        OrderEventType.FULLY_FILLED to onFullFill
    )

    fun callParams(block: OrderCallParamsBuilder.() -> Unit)
    {
        callParams = OrderCallParamsBuilder(block)
    }

    private fun build() = OrderTaskParams(
        callParams = callParams,
        eventParams = OrderTaskEventParams(
            eventData = SubmitEventData(),
            eventHandlers = eventHandlers
        )
    )

    companion object
    {
        operator fun invoke(block: OrderSubmitParamsBuilder.() -> Unit) = OrderSubmitParamsBuilder()
            .apply(block)
            .build()
    }
}