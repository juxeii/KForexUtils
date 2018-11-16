package com.jforex.kforexutils.order.event.handler

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.order.event.OrderEventsConfigurationParams
import com.jforex.kforexutils.order.event.subscribeToOrderEvents
import com.jforex.kforexutils.order.extension.kForexUtils
import com.jforex.kforexutils.order.task.OrderEventHandlerParams
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.zipWith

internal fun registerEventHandlerParams(
    order: IOrder,
    eventHandlerParams: OrderEventHandlerParams
) = with(order.kForexUtils.handlerObservables) {
    val eventsConfiguration = OrderEventsConfigurationParams(
        order = order,
        eventHandlers = eventHandlerParams.eventHandlers,
        finishEventTypes = eventHandlerParams.eventData.finishEventTypes,
        completionCall = {}
    )
    if (eventHandlerParams.eventData.handlerType == OrderEventHandlerType.CHANGE)
    {
        eventsConfiguration.copy(completionCall = { completionTriggers.accept(Unit) })
        changeEventHandlers.accept(eventsConfiguration)
    } else subscribeToOrderEvents(orderEvents = orderEvents, params = eventsConfiguration)
}

internal fun subscribeToCompletionAndHandlers(handlerObservables: OrderEventHandlerObservables) =
    with(handlerObservables) {
        completionTriggers
            .zipWith(changeEventHandlers)
            .map { it.second }
            .subscribeBy(onNext = { subscribeToOrderEvents(orderEvents = orderEvents, params = it) })

        completionTriggers.accept(Unit)
    }