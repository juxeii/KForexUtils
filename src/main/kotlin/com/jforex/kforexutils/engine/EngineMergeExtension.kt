package com.jforex.kforexutils.engine

import arrow.data.runId
import com.dukascopy.api.IEngine
import com.dukascopy.api.IOrder
import com.jforex.kforexutils.order.changeToCallWithOrderInit
import com.jforex.kforexutils.order.event.OrderEvent
import com.jforex.kforexutils.order.event.handler.data.MergeEventData
import com.jforex.kforexutils.order.task.OrderTaskParams
import com.jforex.kforexutils.order.task.builders.OrderCallHandlerBuilder
import com.jforex.kforexutils.order.task.runOrderTask
import com.jforex.kforexutils.settings.TradingSettings
import io.reactivex.Observable

fun IEngine.merge(
    label: String,
    orders: Collection<IOrder>,
    comment: String = TradingSettings.defaultMergeComment,
    block: OrderCallHandlerBuilder.() -> Unit = {}
): Observable<OrderEvent>
{
    val mergeCall = {
        mergeOrders(
            label,
            comment,
            orders
        )
    }
    return runOrderTask(
        orderCallable = changeToCallWithOrderInit(mergeCall),
        taskParams = OrderTaskParams(OrderCallHandlerBuilder(block), MergeEventData())
    ).runId(kForexUtils)
}