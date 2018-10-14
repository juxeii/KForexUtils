package com.jforex.kforexutils.order.extension

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.misc.FieldProperty
import com.jforex.kforexutils.order.message.OrderMessageHandler
import com.jforex.kforexutils.order.params.builders.OrderSLParamsBuilder
import com.jforex.kforexutils.order.params.builders.OrderTPParamsBuilder
import com.jforex.kforexutils.order.task.OrderTask

var IOrder.messageHander: OrderMessageHandler by FieldProperty<IOrder, OrderMessageHandler>()
var IOrder.task: OrderTask by FieldProperty<IOrder, OrderTask>()

fun IOrder.isOpened() = state == IOrder.State.OPENED
fun IOrder.isFilled() = state == IOrder.State.FILLED
fun IOrder.isClosed() = state == IOrder.State.CLOSED
fun IOrder.isCanceled() = state == IOrder.State.CANCELED
fun IOrder.isConditional() = orderCommand.isConditional

@Synchronized
fun IOrder.setSL(price: Double, block: OrderSLParamsBuilder.() -> Unit)
{
    val params = OrderSLParamsBuilder(price, block)
    task.setSL(this, params)
}

@Synchronized
fun IOrder.setTP(price: Double, block: OrderTPParamsBuilder.() -> Unit)
{
    val params = OrderTPParamsBuilder(price, block)
    task.setTP(this, params)
}