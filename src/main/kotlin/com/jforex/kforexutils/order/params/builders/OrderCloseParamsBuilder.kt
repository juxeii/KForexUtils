package com.jforex.kforexutils.order.params.builders

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.order.params.OrderCloseParams
import com.jforex.kforexutils.order.params.actions.OrderCloseActions
import com.jforex.kforexutils.order.params.actions.builders.OrderCloseActionsBuilder
import com.jforex.kforexutils.settings.TradingSettings

fun orderCloseParams(order: IOrder, block: OrderCloseParamsBuilder.() -> Unit) =
    OrderCloseParamsBuilder(order)
        .apply(block)
        .build()

@OrderDsl
class OrderCloseParamsBuilder(private val order: IOrder)
{
    var amount = 0.0
    var price = 0.0
    var slippage = TradingSettings.defaultCloseSlippage
    var closeActions = OrderCloseActions()

    fun closeActions(block: OrderCloseActionsBuilder.() -> Unit)
    {
        closeActions = OrderCloseActionsBuilder()
            .apply(block)
            .build()
    }

    fun build() = OrderCloseParams(
        order,
        closeActions,
        amount,
        price,
        slippage
    )
}