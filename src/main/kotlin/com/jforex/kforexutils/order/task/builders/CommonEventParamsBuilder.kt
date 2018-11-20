package com.jforex.kforexutils.order.task.builders

import com.jforex.kforexutils.misc.EventHandlers
import com.jforex.kforexutils.misc.emptyAction
import com.jforex.kforexutils.misc.emptyErrorConsumer
import com.jforex.kforexutils.misc.emptyOrderEventConsumer
import com.jforex.kforexutils.order.event.OrderEventSubscribeParams

abstract class CommonEventParamsBuilder {
    var onComplete = emptyAction
    var onError = emptyErrorConsumer

    protected abstract fun createMap(): EventHandlers

    protected fun build() = OrderEventSubscribeParams(
        eventHandlers = createMap().filterValues { it != emptyOrderEventConsumer },
        onComplete = onComplete,
        onError = onError
    )
}