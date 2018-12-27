package com.jforex.kforexutils.misc

import com.dukascopy.api.IContext
import com.dukascopy.api.IMessage
import com.jakewharton.rxrelay2.PublishRelay
import com.jforex.kforexutils.engine.kForexUtils
import com.jforex.kforexutils.message.MessageGateway
import com.jforex.kforexutils.message.MessageToOrderEventType
import com.jforex.kforexutils.order.event.OrderEventGateway
import com.jforex.kforexutils.order.event.handler.OrderEventHandlerObservables
import com.jforex.kforexutils.price.BarQuote
import com.jforex.kforexutils.price.TickQuote
import com.jforex.kforexutils.settings.PlatformSettings
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.zipWith
import org.aeonbits.owner.ConfigFactory

lateinit var kForexUtils: KForexUtils

fun initKForexUtils(context: IContext)
{
    kForexUtils = KForexUtils(context)
}

class KForexUtils(val context: IContext)
{
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
        eventRelays = PublishRelay.create()
    )
    val platformSettings: PlatformSettings = ConfigFactory.create(PlatformSettings::class.java)
    val barQuotes: PublishRelay<BarQuote> = PublishRelay.create()
    val tickQuotes: PublishRelay<TickQuote> = PublishRelay.create()

    init
    {
        engine.kForexUtils = this
        subscribeToCompletionAndHandlers(this)
    }

    fun onBarQuote(barQuote: BarQuote) = barQuotes.accept(barQuote)

    fun onTickQuote(tickQuote: TickQuote) = tickQuotes.accept(tickQuote)
}

internal fun subscribeToCompletionAndHandlers(kForexUtils: KForexUtils) =
    with(kForexUtils.handlerObservables) {
        completionTriggers
            .zipWith(eventRelays)
            .map { it.second }
            .subscribeBy(onNext = { relay ->
                kForexUtils
                    .orderEvents
                    .subscribeBy(onNext = { relay.accept(it) })
            })

        completionTriggers.accept(Unit)
    }