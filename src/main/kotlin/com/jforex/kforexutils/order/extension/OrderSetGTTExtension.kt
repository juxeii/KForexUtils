package com.jforex.kforexutils.order.extension

import arrow.data.runId
import com.dukascopy.api.IOrder
import com.jforex.kforexutils.order.changeToCallableCall
import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.event.OrderGTTEvent
import com.jforex.kforexutils.order.event.handler.data.ChangeEventData
import com.jforex.kforexutils.order.task.OrderTaskParams
import com.jforex.kforexutils.order.task.builders.OrderCallHandlerBuilder
import com.jforex.kforexutils.order.task.runOrderTask
import io.reactivex.Observable

@Suppress("UNCHECKED_CAST")
fun IOrder.setGTT(
    gtt: Long,
    block: OrderCallHandlerBuilder.() -> Unit = {}
) = runOrderTask(
    orderCallable = changeToCallableCall(this) { goodTillTime = gtt },
    taskParams = OrderTaskParams(OrderCallHandlerBuilder(block), ChangeEventData(OrderEventType.CHANGED_GTT))
).runId(kForexUtils) as Observable<OrderGTTEvent>