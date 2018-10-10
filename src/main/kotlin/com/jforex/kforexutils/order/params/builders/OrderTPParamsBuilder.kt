package com.jforex.kforexutils.order.params.builders

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.order.params.OrderTPParams
import com.jforex.kforexutils.order.params.actions.OrderTPActions
import com.jforex.kforexutils.order.params.actions.builders.OrderTPActionsBuilder

fun orderTPParams(
    order: IOrder,
    price: Double,
    block: OrderTPParamsBuilder.() -> Unit
) =
    OrderTPParamsBuilder(
        order,
        price
    )
        .apply(block)
        .build()

@OrderDsl
class OrderTPParamsBuilder(
    private val order: IOrder,
    private val price: Double
)
{
    private var tpActions = OrderTPActions()

    fun tpActions(block: OrderTPActionsBuilder.() -> Unit)
    {
        tpActions = OrderTPActionsBuilder()
            .apply(block)
            .build()
    }

    fun build() = OrderTPParams(
        order = order,
        price = price,
        tpActions = tpActions
    )
}