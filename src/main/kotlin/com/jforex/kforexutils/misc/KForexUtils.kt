package com.jforex.kforexutils.misc

import com.dukascopy.api.IContext
import com.dukascopy.api.IMessage
import com.dukascopy.api.Instrument
import com.jakewharton.rxrelay2.BehaviorRelay
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

typealias Quotes = Map<Instrument, TickQuote>

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

    val quotesMap: BehaviorRelay<Quotes> = BehaviorRelay.createDefault(emptyMap())

    init
    {
        engine.kForexUtils = this
        subscribeToCompletionAndHandlers(this)
        tickQuotes.subscribeBy(onNext = { saveQuote(it) })
    }

    fun getQuotes() = quotesMap.value!!

    fun updateQuotes(quote: TickQuote) = getQuotes().plus(Pair(quote.instrument, quote))

    fun saveQuote(quote: TickQuote)
    {
        quotesMap.accept(updateQuotes(quote))
    }

    fun onTickQuote(tickQuote: TickQuote) = tickQuotes.accept(tickQuote)

    fun onBarQuote(barQuote: BarQuote) = barQuotes.accept(barQuote)
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