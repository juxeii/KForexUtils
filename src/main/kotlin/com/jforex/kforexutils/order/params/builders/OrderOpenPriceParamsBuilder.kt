package com.jforex.kforexutils.order.params.builders

import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.order.params.OrderOpenPriceParams
import com.jforex.kforexutils.order.params.actions.OrderOpenPriceActions
import com.jforex.kforexutils.order.params.actions.builders.OrderOpenPriceActionsBuilder
import com.jforex.kforexutils.settings.TradingSettings

@OrderDsl
class OrderOpenPriceParamsBuilder(private val openPrice: Double)
{
    var slippage = TradingSettings.defaultOpenPriceSlippage
    private var actions = OrderOpenPriceActions()

    fun actions(block: OrderOpenPriceActionsBuilder.() -> Unit)
    {
        actions = OrderOpenPriceActionsBuilder(block)
    }

    private fun build() = OrderOpenPriceParams(
        openPrice = openPrice,
        slippage = slippage,
        actions = actions
    )

    companion object
    {
        operator fun invoke(openPrice: Double, block: OrderOpenPriceParamsBuilder.() -> Unit) =
            OrderOpenPriceParamsBuilder(openPrice)
                .apply(block)
                .build()
    }
}