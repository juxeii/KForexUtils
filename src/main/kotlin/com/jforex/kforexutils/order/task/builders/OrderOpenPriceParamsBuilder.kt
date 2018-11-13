package com.jforex.kforexutils.order.task.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.misc.emptyOrderCallParams
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.event.handler.data.ChangeEventData
import com.jforex.kforexutils.order.task.OrderTaskEventParams
import com.jforex.kforexutils.order.task.OrderTaskParams

@OrderDsl
class OrderOpenPriceParamsBuilder
{
    private var callParams = emptyOrderCallParams
    var onOpenPriceChange = emptyOrderEventConsumer
    var onOpenPriceReject = emptyOrderEventConsumer
    private val eventHandlers = mapOf(
        OrderEventType.CHANGED_PRICE to onOpenPriceChange,
        OrderEventType.CHANGE_REJECTED to onOpenPriceReject
    )

    fun callParams(block: OrderCallParamsBuilder.() -> Unit)
    {
        callParams = OrderCallParamsBuilder(block)
    }

    private fun build() = OrderTaskParams(
        callParams = callParams,
        eventParams = OrderTaskEventParams(
            eventData = ChangeEventData(OrderEventType.CHANGED_PRICE),
            eventHandlers = eventHandlers
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