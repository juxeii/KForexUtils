package com.jforex.kforexutils.engine

import com.dukascopy.api.IEngine
import com.dukascopy.api.IOrder
import com.jforex.kforexutils.misc.KRunnable
import com.jforex.kforexutils.order.event.handler.data.MergeEventHandlerData
import com.jforex.kforexutils.order.params.builders.OrderMergeParamsBuilder

fun IEngine.merge(
    label: String,
    orders: Collection<IOrder>,
    block: OrderMergeParamsBuilder.() -> Unit = {}
) {
    val params = OrderMergeParamsBuilder(
        label = label,
        orders = orders,
        block = block
    )
    createOrder(
        engineCall = {
            mergeOrders(
                label,
                params.comment,
                orders
            )
        },
        handlerDataProvider = { retryCall: KRunnable -> MergeEventHandlerData(params.actions, retryCall) }
    )
}