package com.jforex.kforexutils.engine

import com.dukascopy.api.IEngine
import com.dukascopy.api.IOrder
import com.jforex.kforexutils.order.task.builders.OrderMergeParamsBuilder
import com.jforex.kforexutils.settings.TradingSettings

fun IEngine.merge(
    label: String,
    orders: Collection<IOrder>,
    comment: String = TradingSettings.defaultMergeComment,
    block: OrderMergeParamsBuilder.() -> Unit = {}
) = createOrder(
    orderCreationCall = {
        mergeOrders(
            label,
            comment,
            orders
        )
    },
    taskParams = OrderMergeParamsBuilder(block)
).run(kForexUtils)