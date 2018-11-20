package com.jforex.kforexutils.order.task.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.order.event.OrderEventType

@OrderDsl
class OpenPriceEventParamsBuilder : CommonEventParamsBuilder()
{
    var onOpenPriceChange = emptyOrderEventConsumer
    var onOpenPriceReject = emptyOrderEventConsumer

    override fun createMap() = mapOf(
        OrderEventType.CHANGED_PRICE to onOpenPriceChange,
        OrderEventType.CHANGE_REJECTED to onOpenPriceReject
    )

    companion object {
        operator fun invoke(block: OpenPriceEventParamsBuilder.() -> Unit = {}) = OpenPriceEventParamsBuilder()
            .apply(block)
            .build()
    }
}