package com.jforex.kforexutils.order

import com.dukascopy.api.IOrder
import com.google.common.collect.MapMaker
import java.util.*

class OrderStore {
    private val orders: Set<IOrder> = Collections.newSetFromMap(
        MapMaker()
            .weakKeys()
            .makeMap<IOrder, Boolean>()
    )

    @Synchronized
    fun add(order: IOrder) = orders.plusElement(order)

    @Synchronized
    fun remove(order: IOrder) = orders.minusElement(order)

    @Synchronized
    fun contains(order: IOrder) = orders.contains(order)
}