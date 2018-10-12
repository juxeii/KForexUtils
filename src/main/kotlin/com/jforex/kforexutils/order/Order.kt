package com.jforex.kforexutils.order

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.order.params.builders.OrderSLParamsBuilder
import com.jforex.kforexutils.order.params.builders.OrderTPParamsBuilder
import com.jforex.kforexutils.order.task.OrderTasks

class Order(
    val jfOrder: IOrder,
    private val orderTasks: OrderTasks
) {
    fun isOpened() = jfOrder.state == IOrder.State.OPENED
    fun isFilled() = jfOrder.state == IOrder.State.FILLED
    fun isClosed() = jfOrder.state == IOrder.State.CLOSED
    fun isCanceled() = jfOrder.state == IOrder.State.CANCELED
    fun isConditional() = jfOrder.orderCommand.isConditional

    @Synchronized
    fun setSL(price: Double, block: OrderSLParamsBuilder.() -> Unit) {
        val params = OrderSLParamsBuilder(price, block)
        orderTasks.setSL(this, params)
    }

    @Synchronized
    fun setTP(price: Double, block: OrderTPParamsBuilder.() -> Unit) {
        val params = OrderTPParamsBuilder(price, block)
        orderTasks.setTP(this, params)
    }
}