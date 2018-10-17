package com.jforex.kforexutils.order.params.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.order.params.OrderCloseParams
import com.jforex.kforexutils.order.params.actions.OrderCloseActions
import com.jforex.kforexutils.order.params.actions.builders.OrderCloseActionsBuilder
import com.jforex.kforexutils.settings.TradingSettings

@OrderDsl
class OrderCloseParamsBuilder
{
    var amount = 0.0
    var price = 0.0
    var slippage = TradingSettings.defaultCloseSlippage
    var closeActions = OrderCloseActions()

    fun closeActions(block: OrderCloseActionsBuilder.() -> Unit)
    {
        closeActions = OrderCloseActionsBuilder(block)
    }

    fun build() = OrderCloseParams(
        closeActions = closeActions,
        amount = amount,
        price = price,
        slippage = slippage
    )

    companion object
    {
        operator fun invoke(block: OrderCloseParamsBuilder.() -> Unit) = OrderCloseParamsBuilder()
            .apply(block)
            .build()
    }
}