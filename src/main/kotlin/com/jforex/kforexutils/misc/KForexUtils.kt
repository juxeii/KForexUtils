package com.jforex.kforexutils.misc

import arrow.data.ReaderApi
import arrow.data.map
import com.dukascopy.api.IContext
import com.dukascopy.api.IMessage
import com.dukascopy.api.IOrder
import com.jakewharton.rxrelay2.PublishRelay
import com.jforex.kforexutils.engine.kForexUtils
import com.jforex.kforexutils.message.MessageGateway
import com.jforex.kforexutils.message.MessageToOrderEventType
import com.jforex.kforexutils.order.event.OrderEventGateway
import com.jforex.kforexutils.order.event.handler.*
import com.jforex.kforexutils.order.task.OrderTaskExecutionParams
import com.jforex.kforexutils.settings.PlatformSettings
import org.aeonbits.owner.ConfigFactory

class KForexUtils(val context: IContext) {
    val engine = context.engine
    private val messagePublisher: PublishRelay<IMessage> = PublishRelay.create()
    val messageGateway = MessageGateway(messagePublisher)
    private val orderEventConverter = MessageToOrderEventType()
    val orderMessageGateway = OrderEventGateway(
        messageGateway.messages,
        orderEventConverter
    )
    val orderEvents = orderMessageGateway.observable
    val handlerObservables = OrderEventHandlerObservables(
        orderEvents = orderEvents,
        completionTriggers = PublishRelay.create(),
        changeEventHandlers = PublishRelay.create()
    )
    val platformSettings: PlatformSettings = ConfigFactory.create(PlatformSettings::class.java)

    init {
        engine.kForexUtils = this
        subscribeToCompletionAndHandlers(handlerObservables)
    }
}

internal fun registerHandler(
    order: IOrder,
    params: OrderTaskExecutionParams
) = ReaderApi
    .ask<KForexUtils>()
    .map { kForexUtils ->
        with(kForexUtils.handlerObservables) {
            val executionData = OrderEventExecutionData(order, params)
            if (params.eventParams.eventData.handlerType == OrderEventHandlerType.CHANGE)
                changeEventHandlers.accept(executionData)
            else subscribeToEvents(orderEvents, executionData) {}
        }
    }