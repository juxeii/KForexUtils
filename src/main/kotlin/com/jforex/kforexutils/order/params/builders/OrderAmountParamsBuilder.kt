package com.jforex.kforexutils.order.params.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.order.params.OrderAmountParams
import com.jforex.kforexutils.order.params.actions.OrderAmountActions
import com.jforex.kforexutils.order.params.actions.builders.OrderAmountActionsBuilder

@OrderDsl
class OrderAmountParamsBuilder(private val amount: Double) : OrderRetryBuilderBase() {
    private var actions = OrderAmountActions()

    fun actions(block: OrderAmountActionsBuilder.() -> Unit) {
        actions = OrderAmountActionsBuilder(block)
    }

    fun build() = OrderAmountParams(
        amount = amount,
        actions = actions,
        retry = retry
    )

    companion object {
        operator fun invoke(amount: Double, block: OrderAmountParamsBuilder.() -> Unit) =
            OrderAmountParamsBuilder(amount)
                .apply(block)
                .build()
    }
}