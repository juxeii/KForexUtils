package com.jforex.kforexutils.order.params.builders

import com.jforex.kforexutils.order.params.OrderRetryParams

abstract class OrderRetryBuilderBase {
    var retry = OrderRetryParams()

    fun retry(block: OrderRetryParamsBuilder.() -> Unit) {
        retry = OrderRetryParamsBuilder(block)
    }
}