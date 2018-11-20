package com.jforex.kforexutils.order.task.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.order.event.OrderEventType

@OrderDsl
class CloseEventParamsBuilder : CommonEventParamsBuilder() {
    var onClose = emptyOrderEventConsumer
    var onPartialClose = emptyOrderEventConsumer
    var onCloseReject = emptyOrderEventConsumer

    override fun createMap() = mapOf(
        OrderEventType.CLOSE_OK to onClose,
        OrderEventType.PARTIAL_CLOSE_OK to onPartialClose,
        OrderEventType.CLOSE_REJECTED to onCloseReject
    )

    companion object {
        operator fun invoke(block: CloseEventParamsBuilder.() -> Unit = {}) = CloseEventParamsBuilder()
            .apply(block)
            .build()
    }
}