package com.jforex.kforexutils.engine

import com.dukascopy.api.IEngine
import com.dukascopy.api.IOrder
import com.jforex.kforexutils.order.task.builders.OrderMergeParamsBuilder

fun IEngine.merge(
    label: String,
    orders: Collection<IOrder>,
    comment: String = "",
    block: OrderMergeParamsBuilder.() -> Unit = {}
) = createOrder(
    kForexUtils = kForexUtils,
    engineCall = {
        mergeOrders(
            label,
            comment,
            orders
        )
    },
    taskParams = OrderMergeParamsBuilder(block)
)