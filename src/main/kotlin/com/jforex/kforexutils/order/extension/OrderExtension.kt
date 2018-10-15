package com.jforex.kforexutils.order.extension

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.misc.FieldProperty
import com.jforex.kforexutils.order.message.OrderMessageHandler
import com.jforex.kforexutils.order.task.OrderTask

internal var IOrder.task: OrderTask by FieldProperty<IOrder, OrderTask>()
internal var IOrder.messageHandler: OrderMessageHandler by FieldProperty<IOrder, OrderMessageHandler>()

fun IOrder.isOpened() = state == IOrder.State.OPENED
fun IOrder.isFilled() = state == IOrder.State.FILLED
fun IOrder.isClosed() = state == IOrder.State.CLOSED
fun IOrder.isCanceled() = state == IOrder.State.CANCELED
fun IOrder.isConditional() = orderCommand.isConditional