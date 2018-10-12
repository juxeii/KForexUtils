package com.jforex.kforexutils.order.task

import com.jforex.kforexutils.order.Order
import com.jforex.kforexutils.order.params.OrderSLParams
import com.jforex.kforexutils.order.params.OrderTPParams

data class OrderTasks(
    val setSLTask: OrderSetSLTask,
    val setTPTask: OrderSetTPTask
) {
    fun setSL(
        order: Order,
        params: OrderSLParams
    ) = setSLTask.execute(order, params)

    fun setTP(
        order: Order,
        params: OrderTPParams
    ) = setTPTask.execute(order, params)
}