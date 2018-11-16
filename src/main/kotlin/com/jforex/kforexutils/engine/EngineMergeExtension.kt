package com.jforex.kforexutils.engine

import com.dukascopy.api.IEngine
import com.dukascopy.api.IOrder
import com.jforex.kforexutils.order.task.builders.MergeEventParamsBuilder
import com.jforex.kforexutils.order.task.builders.OrderParamsBuilder
import com.jforex.kforexutils.settings.TradingSettings

fun IEngine.merge(
    label: String,
    orders: Collection<IOrder>,
    comment: String = TradingSettings.defaultMergeComment,
    block: OrderParamsBuilder<MergeEventParamsBuilder>.() -> Unit = {}
) = createOrder(
    orderCreationCall = {
        mergeOrders(
            label,
            comment,
            orders
        )
    },
    taskParams = OrderParamsBuilder(MergeEventParamsBuilder(), block)
).run(kForexUtils)