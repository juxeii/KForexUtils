package com.jforex.kforexutils.order

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.order.message.OrderMessageHandler
import com.jforex.kforexutils.order.params.actions.builders.OrderTPActionsBuilder
import com.jforex.kforexutils.order.task.OrderTasks

class Order(
    val jfOrder: IOrder,
    val orderTasks: OrderTasks,
    val messageHandler: OrderMessageHandler
)
{
    @Synchronized
    fun setTP(price: Double, block: OrderTPActionsBuilder.() -> Unit)
    {
        val actions = OrderTPActionsBuilder(block)
        orderTasks.setTPTask.execute(
            this,
            price,
            actions
        )
    }
}