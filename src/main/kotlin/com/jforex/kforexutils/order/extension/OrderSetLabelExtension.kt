package com.jforex.kforexutils.order.extension

import arrow.core.value
import com.dukascopy.api.IOrder
import com.jforex.kforexutils.order.changeToCallableCall
import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.event.OrderLabelEvent
import com.jforex.kforexutils.order.event.handler.data.ChangeEventData
import com.jforex.kforexutils.order.task.OrderTaskParams
import com.jforex.kforexutils.order.task.builders.OrderCallHandlerBuilder
import com.jforex.kforexutils.order.task.runOrderTask
import io.reactivex.Observable

fun IOrder.setLabel(
    label: String,
    block: OrderCallHandlerBuilder.() -> Unit = {}
): Observable<in OrderLabelEvent> = runOrderTask(
    orderCallable = changeToCallableCall(this) { setLabel(label) },
    taskParams = OrderTaskParams(OrderCallHandlerBuilder(block), ChangeEventData(OrderEventType.CHANGED_LABEL))
).run(kForexUtils).value()