package com.jforex.kforexutils.order.params

import com.jforex.kforexutils.order.params.actions.OrderOpenPriceActions
import com.jforex.kforexutils.settings.TradingSettings

data class OrderOpenPriceParams(
    val openPrice: Double,
    val slippage: Double = TradingSettings.defaultOpenPriceSlippage,
    val actions: OrderOpenPriceActions = OrderOpenPriceActions()
)