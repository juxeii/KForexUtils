package com.jforex.kforexutils.order.task.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.misc.emptyOrderCallParams
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.event.handler.data.ChangeEventData
import com.jforex.kforexutils.order.task.OrderTaskEventParams
import com.jforex.kforexutils.order.task.OrderTaskParams

@OrderDsl
class OrderLabelParamsBuilder
{
    private var callParams = emptyOrderCallParams
    var onLabelChange = emptyOrderEventConsumer
    var onLabelReject = emptyOrderEventConsumer
    private val eventHandlers = mapOf(
        OrderEventType.CHANGED_LABEL to onLabelChange,
        OrderEventType.CHANGE_REJECTED to onLabelReject
    )

    fun callParams(block: OrderCallParamsBuilder.() -> Unit)
    {
        callParams = OrderCallParamsBuilder(block)
    }

    private fun build() = OrderTaskParams(
        callParams = callParams,
        eventParams = OrderTaskEventParams(
            eventData = ChangeEventData(OrderEventType.CHANGED_LABEL),
            eventHandlers = eventHandlers
        )
    )

    companion object
    {
        operator fun invoke(block: OrderLabelParamsBuilder.() -> Unit) =
            OrderLabelParamsBuilder()
                .apply(block)
                .build()
    }
}