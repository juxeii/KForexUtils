package com.jforex.kforexutils.order.task

import com.jforex.kforexutils.order.Order
import com.jforex.kforexutils.order.event.consumer.data.SetTPEventConsumerData
import com.jforex.kforexutils.order.params.actions.OrderTPActions

class OrderSetTPTask(private val taskBase: OrderTaskBase) : OrderTaskBase by taskBase
{
    fun execute(
        order: Order,
        price: Double,
        actions: OrderTPActions
    )
    {
        executeOnStrategyThread(
            orderCall = { -> order.jfOrder.takeProfitPrice = price },
            order = order,
            consumerData = SetTPEventConsumerData(actions),
            basicActions = actions.basicActions
        )
    }
}