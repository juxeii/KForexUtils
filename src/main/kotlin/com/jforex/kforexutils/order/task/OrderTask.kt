package com.jforex.kforexutils.order.task

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.order.params.OrderSLParams
import com.jforex.kforexutils.order.params.OrderTPParams

data class OrderTask(
    val setSLTask: OrderSetSLTask,
    val setTPTask: OrderSetTPTask
) {
    fun setSL(
        order: IOrder,
        params: OrderSLParams
    ) = setSLTask.execute(order, params)

    fun setTP(
        order: IOrder,
        params: OrderTPParams
    ) = setTPTask.execute(order, params)
}