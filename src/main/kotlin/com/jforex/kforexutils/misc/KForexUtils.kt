package com.jforex.kforexutils.misc

import com.dukascopy.api.IContext
import com.dukascopy.api.IMessage
import com.jakewharton.rxrelay2.PublishRelay
import com.jforex.kforexutils.engine.kForexUtils
import com.jforex.kforexutils.message.MessageGateway
import com.jforex.kforexutils.message.MessageToOrderEventType
import com.jforex.kforexutils.order.event.OrderEventGateway
import com.jforex.kforexutils.order.event.handler.OrderEventHandlerObservables
import com.jforex.kforexutils.order.event.subscribeToOrderEvents
import com.jforex.kforexutils.settings.PlatformSettings
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.zipWith
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
        subscribeToCompletionAndHandlers(this)
    }
}

internal fun subscribeToCompletionAndHandlers(kForexUtils: KForexUtils) =
    with(kForexUtils.handlerObservables) {
        completionTriggers
            .zipWith(changeEventHandlers)
            .map { it.second }
            .subscribeBy(onNext = { subscribeToOrderEvents(configuration = it).run(kForexUtils) })

        completionTriggers.accept(Unit)
    }