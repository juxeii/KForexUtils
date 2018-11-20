package com.jforex.kforexutils.engine

import arrow.core.value
import com.dukascopy.api.IEngine
import com.dukascopy.api.IOrder
import com.jforex.kforexutils.order.changeToCallWithOrderInit
import com.jforex.kforexutils.order.event.OrderMergeEvent
import com.jforex.kforexutils.order.event.handler.data.MergeEventData
import com.jforex.kforexutils.order.task.OrderTaskParams
import com.jforex.kforexutils.order.task.builders.OrderCallHandlerBuilder
import com.jforex.kforexutils.order.task.runOrderTask
import com.jforex.kforexutils.settings.TradingSettings
import io.reactivex.Observable

@Suppress("UNCHECKED_CAST")
fun IEngine.merge(
    label: String,
    orders: Collection<IOrder>,
    comment: String = TradingSettings.defaultMergeComment,
    block: OrderCallHandlerBuilder.() -> Unit = {}
): Observable<OrderMergeEvent>
{
    val mergeCall = {
        mergeOrders(
            label,
            comment,
            orders
        )
    }
    return runOrderTask(
        orderCallable = changeToCallWithOrderInit(kForexUtils, mergeCall),
        taskParams = OrderTaskParams(OrderCallHandlerBuilder(block), MergeEventData())
    ).run(kForexUtils).value() as Observable<OrderMergeEvent>
}