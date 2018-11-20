package com.jforex.kforexutils.order.task.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.order.event.OrderEventType

@OrderDsl
class GTTEventParamsBuilder : CommonEventParamsBuilder() {
    var onGTTChange = emptyOrderEventConsumer
    var onGTTReject = emptyOrderEventConsumer

    override fun createMap() = mapOf(
        OrderEventType.CHANGED_GTT to onGTTChange,
        OrderEventType.CHANGE_REJECTED to onGTTReject
    )

    companion object {
        operator fun invoke(block: GTTEventParamsBuilder.() -> Unit = {}) = GTTEventParamsBuilder()
            .apply(block)
            .build()
    }
}