package com.jforex.kforexutils.strategy

import com.dukascopy.api.*
import com.jforex.kforexutils.misc.initKForexUtils
import com.jforex.kforexutils.misc.kForexUtils
import com.jforex.kforexutils.price.BarQuote
import com.jforex.kforexutils.price.TickQuote


class KForexUtilsStrategy : IStrategy
{
    override fun onStart(context: IContext)
    {
        initKForexUtils(context)
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