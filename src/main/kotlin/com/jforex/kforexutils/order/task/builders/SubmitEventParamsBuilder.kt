package com.jforex.kforexutils.order.task.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.order.event.OrderEventType

@OrderDsl
class SubmitEventParamsBuilder : CommonEventParamsBuilder() {
    var onSubmit = emptyOrderEventConsumer
    var onPartialFill = emptyOrderEventConsumer
    var onFullFill = emptyOrderEventConsumer

    override fun createMap() = mapOf(
        OrderEventType.SUBMIT_OK to onSubmit,
        OrderEventType.PARTIALLY_FILLED to onPartialFill,
        OrderEventType.FULLY_FILLED to onFullFill
    )

    companion object {
        operator fun invoke(block: SubmitEventParamsBuilder.() -> Unit = {}) = SubmitEventParamsBuilder()
            .apply(block)
            .build()
    }
}