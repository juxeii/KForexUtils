package com.jforex.kforexutils.order.task

import com.jforex.kforexutils.order.Order
import com.jforex.kforexutils.order.event.consumer.data.SetSLEventConsumerData
import com.jforex.kforexutils.order.params.OrderSLParams

class OrderSetSLTask(private val taskBase: OrderExecution) : OrderExecution by taskBase {
    fun execute(
        order: Order,
        params: OrderSLParams
    ) {
        val actions = params.slActions
        executeOnStrategyThread(
            orderCall = { ->
                order.jfOrder.setStopLossPrice(
                    params.price,
                    params.offerSide,
                    params.trailingStep
                )
            },
            order = order,
            consumerData = SetSLEventConsumerData(actions),
            basicActions = actions.basicActions
        )
    }
}