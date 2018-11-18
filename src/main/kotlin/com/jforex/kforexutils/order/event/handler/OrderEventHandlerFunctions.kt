package com.jforex.kforexutils.order.event.handler

import arrow.data.ReaderApi
import arrow.data.flatMap
import arrow.data.map
import arrow.syntax.function.invoke
import com.dukascopy.api.IOrder
import com.jforex.kforexutils.misc.KForexUtils
import com.jforex.kforexutils.misc.KRunnable
import com.jforex.kforexutils.order.event.OrderEventsConfiguration
import com.jforex.kforexutils.order.event.subscribeToOrderEvents
import com.jforex.kforexutils.order.task.OrderEventHandlerParams

private typealias PartialConfig = (KRunnable) -> OrderEventsConfiguration

internal fun registerEventHandlerParams(
    order: IOrder,
    eventHandlerParams: OrderEventHandlerParams
) = ReaderApi
    .ask<KForexUtils>()
    .flatMap {
        val partialConfig = (::createEventsConfiguration)(order)(eventHandlerParams)
        if (eventHandlerParams.eventData.handlerType == OrderEventHandlerType.CHANGE)
            queueChangeConfig(partialConfig)
        else
            subscribe(partialConfig)
    }

internal fun queueChangeConfig(partialConfig: PartialConfig) = ReaderApi
    .ask<KForexUtils>()
    .flatMap { configWithCompletionTrigger(partialConfig) }
    .flatMap { enqueueNewChangeConfig(it) }

internal fun configWithCompletionTrigger(partialConfig: PartialConfig) = ReaderApi
    .ask<KForexUtils>()
    .map { kForexUtils ->
        partialConfig {
            kForexUtils
                .handlerObservables
                .completionTriggers.accept(Unit)
        }
    }

internal fun enqueueNewChangeConfig(config: OrderEventsConfiguration) = ReaderApi
    .ask<KForexUtils>()
    .map { kForexUtils ->
        kForexUtils
            .handlerObservables
            .changeEventHandlers
            .accept(config)
    }

internal fun subscribe(partialConfig: PartialConfig) = ReaderApi
    .ask<KForexUtils>()
    .map { subscribeToOrderEvents(partialConfig { }) }
    .map { }

internal fun createEventsConfiguration(
    order: IOrder,
    eventHandlerParams: OrderEventHandlerParams,
    completionCall: KRunnable
) = OrderEventsConfiguration(
    order = order,
    handlers = eventHandlerParams.eventHandlers,
    finishTypes = eventHandlerParams.eventData.finishEventTypes,
    completionCall = completionCall
)
