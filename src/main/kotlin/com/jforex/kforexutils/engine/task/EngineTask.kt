package com.jforex.kforexutils.engine.task

import com.dukascopy.api.IEngine
import com.jforex.kforexutils.order.params.OrderSubmitParams

data class EngineTask(val submitTask: EngineSubmitTask)
{
    fun submit(
        engine: IEngine,
        params: OrderSubmitParams
    ) = submitTask.execute(engine, params)
}