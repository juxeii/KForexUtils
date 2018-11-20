package com.jforex.kforexutils.order.extension

import arrow.core.value
import com.dukascopy.api.IOrder
import com.jforex.kforexutils.order.changeToCallableCall
import com.jforex.kforexutils.order.event.OrderAmountEvent
import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.event.handler.data.ChangeEventData
import com.jforex.kforexutils.order.task.OrderTaskParams
import com.jforex.kforexutils.order.task.builders.OrderCallHandlerBuilder
import com.jforex.kforexutils.order.task.runOrderTask
import io.reactivex.Observable

fun IOrder.setAmount(
    amount: Double,
    block: OrderCallHandlerBuilder.() -> Unit = {}
): Observable<in OrderAmountEvent> = runOrderTask(
    orderCallable = changeToCallableCall(this) { requestedAmount = amount },
    taskParams = OrderTaskParams(OrderCallHandlerBuilder(block), ChangeEventData(OrderEventType.CHANGED_AMOUNT))
).run(kForexUtils).value()