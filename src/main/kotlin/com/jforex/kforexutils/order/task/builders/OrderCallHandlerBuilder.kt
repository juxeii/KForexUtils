package com.jforex.kforexutils.order.task.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.misc.emptyAction
import com.jforex.kforexutils.misc.emptyErrorConsumer
import com.jforex.kforexutils.misc.emptyOrderConsumer
import com.jforex.kforexutils.order.task.OrderCallHandlers

@OrderDsl
class OrderCallHandlerBuilder
{
    var onStart = emptyAction
    var onSuccess = emptyOrderConsumer
    var onError = emptyErrorConsumer

    private fun build() = OrderCallHandlers(
        onStart = onStart,
        onSuccess = onSuccess,
        onError = onError
    )

    companion object
    {
        operator fun invoke(block: OrderCallHandlerBuilder.() -> Unit) = OrderCallHandlerBuilder()
            .apply(block)
            .build()
    }
}