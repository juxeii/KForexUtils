package com.jforex.kforexutils.order.params

import com.jforex.kforexutils.order.params.actions.OrderCloseActions
import com.jforex.kforexutils.settings.TradingSettings

data class OrderCloseParams(
    val amount: Double = 0.0,
    val price: Double = 0.0,
    val slippage: Double = TradingSettings.defaultCloseSlippage,
    val actions: OrderCloseActions = OrderCloseActions(),
    val retry: OrderRetryParams = OrderRetryParams()
)