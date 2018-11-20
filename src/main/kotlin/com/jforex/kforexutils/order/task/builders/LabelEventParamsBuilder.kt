package com.jforex.kforexutils.order.task.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.order.event.OrderEventType

@OrderDsl
class LabelEventParamsBuilder : CommonEventParamsBuilder() {
    var onLabelChange = emptyOrderEventConsumer
    var onLabelReject = emptyOrderEventConsumer

    override fun createMap() = mapOf(
        OrderEventType.CHANGED_LABEL to onLabelChange,
        OrderEventType.CHANGE_REJECTED to onLabelReject
    )

    companion object {
        operator fun invoke(block: LabelEventParamsBuilder.() -> Unit = {}) = LabelEventParamsBuilder()
            .apply(block)
            .build()
    }
}