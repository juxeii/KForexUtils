package com.jforex.kforexutils.order.task.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.event.handler.data.ChangeEventData
import com.jforex.kforexutils.order.task.OrderEventHandlerParams

@OrderDsl
class AmountEventParamsBuilder : IParamsBuilder<AmountEventParamsBuilder>
{
    var onAmountChange = emptyOrderEventConsumer
    var onAmountReject = emptyOrderEventConsumer

    private fun getEventHandlerParams() = OrderEventHandlerParams(
        eventData = ChangeEventData(OrderEventType.CHANGED_AMOUNT),
        eventHandlers = mapOf(
            OrderEventType.CHANGED_AMOUNT to onAmountChange,
            OrderEventType.CHANGE_REJECTED to onAmountReject
        )
    )

    override fun build(block: AmountEventParamsBuilder.() -> Unit) = apply(block).getEventHandlerParams()
}