package com.jforex.kforexutils.order.task

import com.jforex.kforexutils.order.event.consumer.data.SetTPEventConsumerData
import com.jforex.kforexutils.order.params.OrderTPParams

class OrderSetTPTask(private val taskBase: OrderExecution) : OrderExecution by taskBase {
    fun execute(
        order: Order,
        params: OrderTPParams
    ) {
        val actions = params.tpActions
        executeOnStrategyThread(
            orderCall = { -> order.jfOrder.takeProfitPrice = params.price },
            order = order,
            consumerData = SetTPEventConsumerData(actions),
            basicActions = actions.basicActions
        )
    }
}