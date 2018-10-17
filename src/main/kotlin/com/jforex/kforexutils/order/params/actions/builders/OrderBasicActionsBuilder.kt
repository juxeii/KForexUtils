package com.jforex.kforexutils.order.params.actions.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.misc.emptyAction
import com.jforex.kforexutils.misc.emptyErrorConsumer
import com.jforex.kforexutils.order.params.actions.OrderBasicActions

@OrderDsl
class OrderBasicActionsBuilder
{
    var onStart = emptyAction
    var onComplete = emptyAction
    var onError = emptyErrorConsumer

    fun build() = OrderBasicActions(
        onStart,
        onComplete,
        onError
    )

    companion object
    {
        operator fun invoke(block: OrderBasicActionsBuilder.() -> Unit) = OrderBasicActionsBuilder()
            .apply(block)
            .build()
    }
}