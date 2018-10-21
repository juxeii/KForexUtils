package com.jforex.kforexutils.order.params.builders

import com.dukascopy.api.IEngine
import com.dukascopy.api.Instrument
import com.jforex.kforexutils.misc.OrderDsl
import com.jforex.kforexutils.order.params.OrderSubmitParams
import com.jforex.kforexutils.order.params.actions.OrderSubmitActions
import com.jforex.kforexutils.order.params.actions.builders.OrderSubmitActionsBuilder
import com.jforex.kforexutils.settings.TradingSettings

@OrderDsl
class OrderSubmitParamsBuilder(
    private val label: String,
    private val instrument: Instrument,
    private val orderCommand: IEngine.OrderCommand,
    private val amount: Double
)
{
    var price = TradingSettings.noPreferredPrice
    var slippage = TradingSettings.defaultSlippage
    var stopLossPrice = TradingSettings.noSLPrice
    var takeProfitPrice = TradingSettings.noTPPrice
    var goodTillTime = TradingSettings.defaultGTT
    var comment = TradingSettings.defaultComment
    var actions = OrderSubmitActions()

    fun actions(block: OrderSubmitActionsBuilder.() -> Unit)
    {
        actions = OrderSubmitActionsBuilder(block)
    }

    fun build() = OrderSubmitParams(
        label = label,
        instrument = instrument,
        orderCommand = orderCommand,
        amount = amount,
        price = price,
        slippage = slippage,
        stopLossPrice = stopLossPrice,
        takeProfitPrice = takeProfitPrice,
        goodTillTime = goodTillTime,
        comment = comment,
        actions = actions
    )

    companion object
    {
        operator fun invoke(
            label: String,
            instrument: Instrument,
            orderCommand: IEngine.OrderCommand,
            amount: Double,
            block: OrderSubmitParamsBuilder.() -> Unit
        ) = OrderSubmitParamsBuilder(
            label,
            instrument,
            orderCommand,
            amount
        )
            .apply(block)
            .build()
    }
}