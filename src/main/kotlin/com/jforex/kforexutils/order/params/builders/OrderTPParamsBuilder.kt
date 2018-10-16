package com.jforex.kforexutils.order.params.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.order.params.OrderTPParams
import com.jforex.kforexutils.order.params.actions.OrderTPActions
import com.jforex.kforexutils.order.params.actions.builders.OrderTPActionsBuilder

@OrderDsl
class OrderTPParamsBuilder(private val price: Double) {
    private var actions = OrderTPActions()

    fun actions(block: OrderTPActionsBuilder.() -> Unit) {
        actions = OrderTPActionsBuilder()
            .apply(block)
            .build()
    }

    fun build() = OrderTPParams(
        price = price,
        actions = actions
    )

    companion object {
        operator fun invoke(price: Double, block: OrderTPParamsBuilder.() -> Unit) =
            OrderTPParamsBuilder(price)
                .apply(block)
                .build()
    }
}