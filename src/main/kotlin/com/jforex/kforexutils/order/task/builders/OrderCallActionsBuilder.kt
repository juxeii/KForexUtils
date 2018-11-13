package com.jforex.kforexutils.order.task.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.misc.emptyAction
import com.jforex.kforexutils.misc.emptyErrorConsumer
import com.jforex.kforexutils.misc.emptyOrderConsumer
import com.jforex.kforexutils.order.task.OrderCallActions

@OrderDsl
class OrderCallActionsBuilder
{
    var onStart = emptyAction
    var onSuccess = emptyOrderConsumer
    var onError = emptyErrorConsumer

    private fun build() = OrderCallActions(
        onStart = onStart,
        onSuccess = onSuccess,
        onError = onError
    )

    companion object
    {
        operator fun invoke(block: OrderCallActionsBuilder.() -> Unit) = OrderCallActionsBuilder()
            .apply(block)
            .build()
    }
}