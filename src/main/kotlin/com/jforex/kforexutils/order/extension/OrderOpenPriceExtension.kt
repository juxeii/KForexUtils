package com.jforex.kforexutils.order.extension

import arrow.data.runId
import com.dukascopy.api.IOrder
import com.jforex.kforexutils.order.changeToCallableCall
import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.event.OrderOpenPriceEvent
import com.jforex.kforexutils.order.event.handler.data.ChangeEventData
import com.jforex.kforexutils.order.task.OrderTaskParams
import com.jforex.kforexutils.order.task.builders.OrderCallHandlerBuilder
import com.jforex.kforexutils.order.task.runOrderTask
import com.jforex.kforexutils.settings.TradingSettings
import io.reactivex.Observable

@Suppress("UNCHECKED_CAST")
fun IOrder.setOpenPrice(
    openPrice: Double,
    slippage: Double = TradingSettings.defaultOpenPriceSlippage,
    block: OrderCallHandlerBuilder.() -> Unit = {}
) = runOrderTask(
    orderCallable = changeToCallableCall(this) { setOpenPrice(openPrice, slippage) },
    taskParams = OrderTaskParams(OrderCallHandlerBuilder(block), ChangeEventData(OrderEventType.CHANGED_PRICE))
).runId(kForexUtils) as Observable<OrderOpenPriceEvent>