package com.jforex.kforexutils.order.extension

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.order.event.consumer.OrderEventConsumer
import com.jforex.kforexutils.order.event.consumer.data.OrderEventConsumerData
import com.jforex.kforexutils.order.message.OrderMessageHandlerStore

fun IOrder.isOpened() = state == IOrder.State.OPENED
fun IOrder.isFilled() = state == IOrder.State.FILLED
fun IOrder.isClosed() = state == IOrder.State.CLOSED
fun IOrder.isCanceled() = state == IOrder.State.CANCELED
fun IOrder.isConditional() = orderCommand.isConditional

val IOrder.messageHandler
    get() = OrderMessageHandlerStore.get(this)

fun IOrder.registerEventConsumer(consumerData: OrderEventConsumerData) {
    val consumer = OrderEventConsumer(consumerData)
    messageHandler.ifPresent { it.registerConsumer(consumer) }
}