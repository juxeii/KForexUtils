package com.jforex.kforexutils.order

import com.dukascopy.api.IOrder
import com.google.common.collect.MapMaker
import java.util.*

class OrderStore {
    private val orderSet: Set<Order> = Collections.newSetFromMap(
        MapMaker()
            .weakKeys()
            .makeMap<Order, Boolean>()
    )
    private val orderByJFOrder = MapMaker()
        .weakKeys()
        .makeMap<IOrder, Order>()

    @Synchronized
    fun add(order: Order) {
        orderSet.plusElement(order)
        orderByJFOrder.computeIfAbsent(order.jfOrder) { order }
    }

    @Synchronized
    fun getForJFOrder(jfOrder: IOrder) = Optional.ofNullable(orderByJFOrder[jfOrder])

    @Synchronized
    fun remove(order: Order) {
        orderSet.minusElement(order)
        orderByJFOrder.remove(order.jfOrder)
    }

    fun containsJFOrder(jfOrder: IOrder) = orderByJFOrder.containsKey(jfOrder)
}