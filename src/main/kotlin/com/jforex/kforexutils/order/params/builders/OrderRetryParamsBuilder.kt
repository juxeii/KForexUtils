package com.jforex.kforexutils.order.params.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.order.params.OrderRetryParams

@OrderDsl
class OrderRetryParamsBuilder {
    var attempts = 0
    var delay = 0L

    fun build() = OrderRetryParams(
        attempts = attempts,
        delay = delay
    )

    companion object {
        operator fun invoke(block: OrderRetryParamsBuilder.() -> Unit) =
            OrderRetryParamsBuilder()
                .apply(block)
                .build()
    }
}