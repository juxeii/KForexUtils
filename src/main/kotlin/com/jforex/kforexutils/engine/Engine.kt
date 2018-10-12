package com.jforex.kforexutils.engine

import com.dukascopy.api.IEngine
import com.dukascopy.api.Instrument
import com.jforex.kforexutils.engine.task.EngineTasks
import com.jforex.kforexutils.order.params.builders.OrderSubmitParamsBuilder

class Engine(
    val jfEngine: IEngine,
    val engineTasks: EngineTasks
) {
    @Synchronized
    fun submit(
        label: String,
        instrument: Instrument,
        orderCommand: IEngine.OrderCommand,
        amount: Double,
        block: OrderSubmitParamsBuilder.() -> Unit
    ) {
        val params = OrderSubmitParamsBuilder(
            label = label,
            instrument = instrument,
            orderCommand = orderCommand,
            amount = amount,
            block = block
        )
        engineTasks
            .submitTask
            .execute(jfEngine, params)
    }
}