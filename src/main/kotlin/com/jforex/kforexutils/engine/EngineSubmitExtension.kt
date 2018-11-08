package com.jforex.kforexutils.engine

import com.dukascopy.api.IEngine
import com.dukascopy.api.Instrument
import com.jforex.kforexutils.misc.KRunnable
import com.jforex.kforexutils.order.event.handler.data.SubmitEventHandlerData
import com.jforex.kforexutils.order.params.OrderSubmitParams
import com.jforex.kforexutils.order.params.builders.OrderSubmitParamsBuilder

fun IEngine.submit(
    label: String,
    instrument: Instrument,
    orderCommand: IEngine.OrderCommand,
    amount: Double,
    block: OrderSubmitParamsBuilder.() -> Unit = {}
)
{
    val params = OrderSubmitParamsBuilder(
        label = label,
        instrument = instrument,
        orderCommand = orderCommand,
        amount = amount,
        block = block
    )
    createOrder(
        engineCall = createSubmitCall(this, params),
        handlerDataProvider = { retryCall: KRunnable -> SubmitEventHandlerData(params.actions, retryCall) }
    )
}

private fun createSubmitCall(engine: IEngine, params: OrderSubmitParams) = {
    engine.submitOrder(
        params.label,
        params.instrument,
        params.orderCommand,
        params.amount,
        params.price,
        params.slippage,
        params.stopLossPrice,
        params.takeProfitPrice,
        params.goodTillTime,
        params.comment
    )
}