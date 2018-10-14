package com.jforex.kforexutils.order.message

import com.google.common.collect.MapMaker
import java.util.*

class OrderMessageHandlerStore(private val orderMessageGateway: OrderMessageGateway) {
    private val handlerByOrder = MapMaker()
        .weakKeys()
        .makeMap<Order, OrderMessageHandler>()

    fun create(order: Order): OrderMessageHandler =
        handlerByOrder.computeIfAbsent(order) { OrderMessageHandler(order, orderMessageGateway) }

    fun get(order: Order) = Optional.ofNullable(handlerByOrder[order])

    fun remove(order: Order) = handlerByOrder.remove(order)
}