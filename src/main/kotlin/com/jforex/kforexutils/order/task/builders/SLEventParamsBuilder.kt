package com.jforex.kforexutils.order.task.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.order.event.OrderEventType

@OrderDsl
class SLEventParamsBuilder : CommonEventParamsBuilder() {
    var onSLChange = emptyOrderEventConsumer
    var onSLReject = emptyOrderEventConsumer

    override fun createMap() = mapOf(
        OrderEventType.CHANGED_SL to onSLChange,
        OrderEventType.CHANGE_REJECTED to onSLReject
    )

    companion object {
        operator fun invoke(block: SLEventParamsBuilder.() -> Unit = {}) = SLEventParamsBuilder()
            .apply(block)
            .build()
    }
}