package com.jforex.kforexutils.engine.task

import com.dukascopy.api.IEngine
import com.jforex.kforexutils.order.event.consumer.data.SubmitEventConsumerData
import com.jforex.kforexutils.order.params.OrderSubmitParams

class EngineSubmitTask(private val orderCreation: EngineOrderCreation) : EngineOrderCreation by orderCreation
{
    fun execute(engine: IEngine, params: OrderSubmitParams)
    {
        val actions = params.submitActions
        createOnStrategyThread(
            engineCall = createSubmitCall(engine, params),
            consumerData = SubmitEventConsumerData(actions),
            basicActions = actions.basicActions
        )
    }

    private fun createSubmitCall(engine: IEngine, params: OrderSubmitParams) = { ->
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
}