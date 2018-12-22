package com.jforex.kforexutils.engine

import arrow.data.runId
import com.dukascopy.api.IEngine
import com.dukascopy.api.Instrument
import com.jforex.kforexutils.order.changeToCallWithOrderInit
import com.jforex.kforexutils.order.event.OrderEvent
import com.jforex.kforexutils.order.event.handler.data.SubmitEventData
import com.jforex.kforexutils.order.task.OrderTaskParams
import com.jforex.kforexutils.order.task.builders.OrderCallHandlerBuilder
import com.jforex.kforexutils.order.task.runOrderTask
import com.jforex.kforexutils.settings.TradingSettings
import io.reactivex.Observable

@Suppress("UNCHECKED_CAST")
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
    block: OrderCallHandlerBuilder.() -> Unit = {}
): Observable<OrderEvent>
{
    val submitCall = {
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
    return runOrderTask(
        orderCallable = changeToCallWithOrderInit(kForexUtils, submitCall),
        taskParams = OrderTaskParams(OrderCallHandlerBuilder(block), SubmitEventData())
    ).runId(kForexUtils)
}