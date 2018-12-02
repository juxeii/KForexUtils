package com.jforex.kforexutils.strategy

import com.dukascopy.api.*
import com.jakewharton.rxrelay2.BehaviorRelay
import com.jforex.kforexutils.misc.KForexUtils
import com.jforex.kforexutils.price.BarQuote
import com.jforex.kforexutils.price.TickQuote
import io.reactivex.Observable


class KForexUtilsStrategy : IStrategy
{
    private val relay: BehaviorRelay<KForexUtils> = BehaviorRelay.create()
    private lateinit var kForexUtils: KForexUtils

    fun kForexUtilsSingle(): Observable<KForexUtils> = relay

    override fun onStart(context: IContext)
    {
        kForexUtils = KForexUtils(context)
        relay.accept(kForexUtils)
    }

    override fun onMessage(message: IMessage)
    {
        kForexUtils
            .messageGateway
            .onMessage(message)
    }

    override fun onBar(
        instrument: Instrument,
        period: Period,
        askBar: IBar,
        bidBar: IBar
    )
    {
        val barQuote = BarQuote(
            instrument,
            period,
            askBar,
            bidBar
        )
        kForexUtils.onBarQuote(barQuote)
    }

    override fun onTick(
        instrument: Instrument,
        tick: ITick
    )
    {
        val tickQuote = TickQuote(instrument, tick)
        kForexUtils.onTickQuote(tickQuote)
    }

    override fun onStop()
    {
    }

    override fun onAccount(account: IAccount?)
    {
    }
}