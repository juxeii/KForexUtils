package com.jforex.kforexutils.order.params.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.order.params.OrderLabelParams
import com.jforex.kforexutils.order.params.actions.OrderLabelActions
import com.jforex.kforexutils.order.params.actions.builders.OrderLabelActionsBuilder

@OrderDsl
class OrderLabelParamsBuilder(private val label: String) {
    private var actions = OrderLabelActions()

    fun actions(block: OrderLabelActionsBuilder.() -> Unit) {
        actions = OrderLabelActionsBuilder(block)
    }

    fun build() = OrderLabelParams(
        label = label,
        actions = actions
    )

    companion object {
        operator fun invoke(label: String, block: OrderLabelParamsBuilder.() -> Unit) =
            OrderLabelParamsBuilder(label)
                .apply(block)
                .build()
    }
}