package com.jforex.kforexutils.order.params

import com.dukascopy.api.IEngine.OrderCommand
import com.dukascopy.api.Instrument
import com.jforex.kforexutils.order.params.actions.OrderSubmitActions
import com.jforex.kforexutils.settings.TradingSettings

data class OrderSubmitParams(
    val label: String,
    val instrument: Instrument,
    val orderCommand: OrderCommand,
    val amount: Double,
    val price: Double = TradingSettings.noPreferredPrice,
    val slippage: Double = TradingSettings.defaultSlippage,
    val stopLossPrice: Double = TradingSettings.noSLPrice,
    val takeProfitPrice: Double = TradingSettings.noTPPrice,
    val goodTillTime: Long = TradingSettings.defaultGTT,
    val comment: String = TradingSettings.defaultComment,
    val actions: OrderSubmitActions = OrderSubmitActions(),
    val retry: OrderRetryParams = OrderRetryParams()
)