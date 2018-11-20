package com.jforex.kforexutils.order.task.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.order.event.OrderEventType

@OrderDsl
class TPEventParamsBuilder : CommonEventParamsBuilder() {
    var onTPChange = emptyOrderEventConsumer
    var onTPReject = emptyOrderEventConsumer

    override fun createMap() = mapOf(
        OrderEventType.CHANGED_TP to onTPChange,
        OrderEventType.CHANGE_REJECTED to onTPReject
    )

    companion object {
        operator fun invoke(block: TPEventParamsBuilder.() -> Unit = {}) = TPEventParamsBuilder()
            .apply(block)
            .build()
    }
}