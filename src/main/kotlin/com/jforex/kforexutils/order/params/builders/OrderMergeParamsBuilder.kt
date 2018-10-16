package com.jforex.kforexutils.order.params.builders

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.order.params.OrderMergeParams
import com.jforex.kforexutils.order.params.actions.OrderMergeActions
import com.jforex.kforexutils.order.params.actions.builders.OrderMergeActionsBuilder

@OrderDsl
class OrderMergeParamsBuilder(
    private val label: String,
    private val orders: Collection<IOrder>
) {
    var comment: String = ""
    private var actions = OrderMergeActions()

    fun actions(block: OrderMergeActionsBuilder.() -> Unit) {
        actions = OrderMergeActionsBuilder()
            .apply(block)
            .build()
    }

    fun build() = OrderMergeParams(
        label = label,
        orders = orders,
        comment = comment,
        actions = actions
    )

    companion object {
        operator fun invoke(
            label: String,
            orders: Collection<IOrder>,
            block: OrderMergeParamsBuilder.() -> Unit
        ) =
            OrderMergeParamsBuilder(label, orders)
                .apply(block)
                .build()
    }
}