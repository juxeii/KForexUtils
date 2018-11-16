package com.jforex.kforexutils.engine

import com.dukascopy.api.IEngine
import com.dukascopy.api.Instrument
import com.jforex.kforexutils.order.task.builders.OrderParamsBuilder
import com.jforex.kforexutils.order.task.builders.SubmitEventParamsBuilder
import com.jforex.kforexutils.settings.TradingSettings

fun IEngine.submit(
    label: String,
    instrument: Instrument,
    orderCommand: IEngine.OrderCommand,
    amount: Double,
    price: Double = TradingSettings.noPreferredPrice,
    slippage: Double = TradingSettings.defaultSlippage,
    stopLossPrice: Double = TradingSettings.noSLPrice,
    takeProfitPrice: Double = TradingSettings.noTPPrice,
    goodTillTime: Long = TradingSettings.defaultGTT,
    comment: String = TradingSettings.defaultComment,
    block: OrderParamsBuilder<SubmitEventParamsBuilder>.() -> Unit = {}
) {
    val orderCreationCall = {
        submitOrder(
            label,
            instrument,
            orderCommand,
            amount,
            price,
            slippage,
            stopLossPrice,
            takeProfitPrice,
            goodTillTime,
            comment
        )
    }
    createOrder(
        orderCreationCall = orderCreationCall,
        taskParams = OrderParamsBuilder(SubmitEventParamsBuilder(), block)
    ).run(kForexUtils)
}