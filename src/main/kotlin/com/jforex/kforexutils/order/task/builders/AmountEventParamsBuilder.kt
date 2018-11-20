package com.jforex.kforexutils.order.task.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.order.event.OrderEventType

@OrderDsl
class AmountEventParamsBuilder : CommonEventParamsBuilder()
{
    var onAmountChange = emptyOrderEventConsumer
    var onAmountReject = emptyOrderEventConsumer

    override fun createMap() = mapOf(
        OrderEventType.CHANGED_AMOUNT to onAmountChange,
        OrderEventType.CHANGE_REJECTED to onAmountReject
    )

    companion object
    {
        operator fun invoke(block: AmountEventParamsBuilder.() -> Unit = {}) = AmountEventParamsBuilder()
            .apply(block)
            .build()
    }
}