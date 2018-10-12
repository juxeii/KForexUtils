package com.jforex.kforexutils.engine.task

import com.dukascopy.api.IEngine
import com.jforex.kforexutils.order.event.consumer.data.SubmitEventConsumerData
import com.jforex.kforexutils.order.params.OrderSubmitParams

class EngineSubmitTask(private val engineTask: EngineTask) : EngineTask by engineTask {
    fun execute(jfEngine: IEngine, params: OrderSubmitParams) {
        val actions = params.submitActions
        executeOnStrategyThread(
            engineCall = createSubmitCall(jfEngine, params),
            consumerData = SubmitEventConsumerData(actions),
            basicActions = actions.basicActions
        )
    }

    private fun createSubmitCall(jfEngine: IEngine, params: OrderSubmitParams) = { ->
        jfEngine.submitOrder(
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