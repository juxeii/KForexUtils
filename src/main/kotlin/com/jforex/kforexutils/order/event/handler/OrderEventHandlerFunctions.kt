package com.jforex.kforexutils.order.event.handler

import arrow.data.ReaderApi
import arrow.data.flatMap
import arrow.data.map
import com.dukascopy.api.IOrder
import com.jforex.kforexutils.misc.KForexUtils
import com.jforex.kforexutils.misc.emptyAction
import com.jforex.kforexutils.order.event.OrderEventsConfiguration
import com.jforex.kforexutils.order.event.subscribeToOrderEvents
import com.jforex.kforexutils.order.task.OrderEventHandlerParams

internal fun registerEventHandlerParams(
    order: IOrder,
    eventHandlerParams: OrderEventHandlerParams
) = ReaderApi
    .ask<KForexUtils>()
    .flatMap { createEventsConfiguration(order, eventHandlerParams) }
    .flatMap { registerForEvents(it, eventHandlerParams.eventData.handlerType) }

internal fun createEventsConfiguration(
    order: IOrder,
    eventHandlerParams: OrderEventHandlerParams
) = ReaderApi
    .ask<KForexUtils>()
    .map {
        OrderEventsConfiguration(
            order = order,
            handlers = eventHandlerParams.eventHandlers,
            finishTypes = eventHandlerParams.eventData.finishEventTypes,
            completionCall =
            if (eventHandlerParams.eventData.handlerType == OrderEventHandlerType.CHANGE)
            {
                { it.handlerObservables.completionTriggers.accept(Unit) }
            } else emptyAction
        )
    }

internal fun registerForEvents(
    eventsConfiguration: OrderEventsConfiguration,
    handlerType: OrderEventHandlerType
) = ReaderApi
    .ask<KForexUtils>()
    .map {
        if (handlerType == OrderEventHandlerType.CHANGE)
            it.handlerObservables.changeEventHandlers.accept(eventsConfiguration)
        else subscribeToOrderEvents(it.orderEvents, eventsConfiguration)
    }

