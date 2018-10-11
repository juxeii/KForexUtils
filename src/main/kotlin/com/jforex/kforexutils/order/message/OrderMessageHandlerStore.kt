package com.jforex.kforexutils.order.message

import com.dukascopy.api.IOrder
import com.google.common.collect.MapMaker
import java.util.*

object OrderMessageHandlerStore {
    private val handlerByOrder = MapMaker()
        .weakKeys()
        .makeMap<IOrder, OrderMessageHandler>()

    fun add(handler: OrderMessageHandler): OrderMessageHandler =
        handlerByOrder.computeIfAbsent(handler.order) { handler }

    fun get(order: IOrder): Optional<OrderMessageHandler> = Optional.ofNullable(handlerByOrder[order])

    fun remove(order: IOrder) = handlerByOrder.remove(order)
}