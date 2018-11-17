package com.jforex.kforexutils.engine

import com.dukascopy.api.IEngine
import com.dukascopy.api.IOrder
import com.jforex.kforexutils.order.changeToCallWithOrderInit
import com.jforex.kforexutils.order.task.builders.MergeEventParamsBuilder
import com.jforex.kforexutils.order.task.builders.OrderParamsBuilder
import com.jforex.kforexutils.order.task.runOrderTask
import com.jforex.kforexutils.settings.TradingSettings

fun IEngine.merge(
    label: String,
    orders: Collection<IOrder>,
    comment: String = TradingSettings.defaultMergeComment,
    block: OrderParamsBuilder<MergeEventParamsBuilder>.() -> Unit = {}
)
{
    val mergeCall = {
        mergeOrders(
            label,
            comment,
            orders
        )
    }
    runOrderTask(
        orderCallable = changeToCallWithOrderInit(kForexUtils, mergeCall),
        taskParams = OrderParamsBuilder(MergeEventParamsBuilder(), block)
    ).run(kForexUtils)
}