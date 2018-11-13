package com.jforex.kforexutils.order.task.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.misc.emptyOrderCallParams
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.event.handler.data.ChangeEventData
import com.jforex.kforexutils.order.task.OrderTaskEventParams
import com.jforex.kforexutils.order.task.OrderTaskParams

@OrderDsl
class OrderSLParamsBuilder
{
    private var callParams = emptyOrderCallParams
    var onSLChange = emptyOrderEventConsumer
    var onSLReject = emptyOrderEventConsumer
    private val eventHandlers = mapOf(
        OrderEventType.CHANGED_SL to onSLChange,
        OrderEventType.CHANGE_REJECTED to onSLReject
    )

    fun callParams(block: OrderCallParamsBuilder.() -> Unit)
    {
        callParams = OrderCallParamsBuilder(block)
    }

    private fun build() = OrderTaskParams(
        callParams = callParams,
        eventParams = OrderTaskEventParams(
            eventData = ChangeEventData(OrderEventType.CHANGED_SL),
            eventHandlers = eventHandlers
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
