package com.jforex.kforexutils.order.event.handler

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.order.event.OrderEventsConfiguration
import com.jforex.kforexutils.order.event.subscribeToOrderEvents
import com.jforex.kforexutils.order.extension.kForexUtils
import com.jforex.kforexutils.order.task.OrderEventHandlerParams
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.zipWith

internal fun registerEventHandlerParams(
    order: IOrder,
    eventHandlerParams: OrderEventHandlerParams
) = with(order.kForexUtils.handlerObservables) {
    val eventsConfiguration = OrderEventsConfiguration(
        order = order,
        handlers = eventHandlerParams.eventHandlers,
        finishTypes = eventHandlerParams.eventData.finishEventTypes,
        completionCall = {}
    )
    if (eventHandlerParams.eventData.handlerType == OrderEventHandlerType.CHANGE)
    {
        changeEventHandlers.accept(eventsConfiguration.copy(completionCall = { completionTriggers.accept(Unit) }))
    } else subscribeToOrderEvents(orderEvents = orderEvents, configuration = eventsConfiguration)
}

internal fun subscribeToCompletionAndHandlers(handlerObservables: OrderEventHandlerObservables) =
    with(handlerObservables) {
        completionTriggers
            .zipWith(changeEventHandlers)
            .map { it.second }
            .subscribeBy(onNext = { subscribeToOrderEvents(orderEvents = orderEvents, configuration = it) })

        completionTriggers.accept(Unit)
    }