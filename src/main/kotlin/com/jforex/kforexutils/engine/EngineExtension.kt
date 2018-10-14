package com.jforex.kforexutils.engine

import com.dukascopy.api.IEngine
import com.dukascopy.api.Instrument
import com.jforex.kforexutils.engine.task.EngineTask
import com.jforex.kforexutils.misc.FieldProperty
import com.jforex.kforexutils.order.params.builders.OrderSubmitParamsBuilder

var IEngine.task: EngineTask by FieldProperty<IEngine, EngineTask>()

fun IEngine.submit(
    label: String,
    instrument: Instrument,
    orderCommand: IEngine.OrderCommand,
    amount: Double,
    block: OrderSubmitParamsBuilder.() -> Unit
)
{
    val params = OrderSubmitParamsBuilder(
        label = label,
        instrument = instrument,
        orderCommand = orderCommand,
        amount = amount,
        block = block
    )
    task.submit(this, params)
}