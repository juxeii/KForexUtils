package com.jforex.kforexutils.order.params.actions.builders

import com.jforex.kforexutils.misc.*
import com.jforex.kforexutils.order.params.actions.OrderBasicActions

@OrderDsl
class OrderBasicActionsBuilder
{
    var onStart: KRunnable = emptyAction
    var onComplete: KRunnable = emptyAction
    var onError: ErrorConsumer = emptyErrorConsumer

    fun build() = OrderBasicActions(
        onStart,
        onComplete,
        onError
    )

}