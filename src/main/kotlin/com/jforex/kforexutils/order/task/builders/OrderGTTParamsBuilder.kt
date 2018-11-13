package com.jforex.kforexutils.order.task.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.misc.emptyOrderCallParams
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.event.handler.data.ChangeEventData
import com.jforex.kforexutils.order.task.OrderTaskEventParams
import com.jforex.kforexutils.order.task.OrderTaskParams

@OrderDsl
class OrderGTTParamsBuilder
{
    private var callParams = emptyOrderCallParams
    var onGTTChange = emptyOrderEventConsumer
    var onGTTReject = emptyOrderEventConsumer
    private val eventHandlers = mapOf(
        OrderEventType.CHANGED_GTT to onGTTChange,
        OrderEventType.CHANGE_REJECTED to onGTTReject
    )

    fun callParams(block: OrderCallParamsBuilder.() -> Unit)
    {
        callParams = OrderCallParamsBuilder(block)
    }

    private fun build() = OrderTaskParams(
        callParams = callParams,
        eventParams = OrderTaskEventParams(
            eventData = ChangeEventData(OrderEventType.CHANGED_GTT),
            eventHandlers = eventHandlers
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