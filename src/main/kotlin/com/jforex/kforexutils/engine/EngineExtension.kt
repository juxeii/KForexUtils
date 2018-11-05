package com.jforex.kforexutils.engine

import arrow.core.Failure
import arrow.core.Success
import com.dukascopy.api.IContext
import com.dukascopy.api.IEngine
import com.dukascopy.api.IOrder
import com.jforex.kforexutils.context.executeTaskOnStrategyThreadBlocking
import com.jforex.kforexutils.misc.FieldProperty
import com.jforex.kforexutils.misc.KCallable
import com.jforex.kforexutils.order.event.OrderEventGateway
import com.jforex.kforexutils.order.event.OrderEventObservable
import com.jforex.kforexutils.order.event.handler.OrderEventHandler
import com.jforex.kforexutils.order.event.handler.OrderEventHandlerQueue
import com.jforex.kforexutils.order.event.handler.data.OrderEventHandlerData
import com.jforex.kforexutils.order.extension.context
import com.jforex.kforexutils.order.extension.eventHandler

internal var IEngine.orderMessageGateway: OrderEventGateway by FieldProperty()
internal var IEngine.context: IContext by FieldProperty()

internal fun IEngine.createOrder(
    engineCall: KCallable<IOrder>,
    handlerData: OrderEventHandlerData
) {
    handlerData.retryCall = { createOrder(engineCall, handlerData) }
    val engineCallWithOrderInitialization = {
        val order = engineCall()
        val filteredOrderEvents = orderMessageGateway
            .observable
            .filter { it.order == order }
        order.context = context
        val orderEventObservable = OrderEventObservable(filteredOrderEvents)
        val handlerQueue = OrderEventHandlerQueue(orderEventObservable)
        order.eventHandler = OrderEventHandler(orderEventObservable, handlerQueue)
        order.eventHandler.register(handlerData)
        order
    }

    with(handlerData.taskActions) {
        onStart()
        when (val taskResult = context.executeTaskOnStrategyThreadBlocking(engineCallWithOrderInitialization))
        {
            is Success -> onSuccess(taskResult.value)
            is Failure -> onError(taskResult.exception)
        }
    }
}