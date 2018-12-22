package com.jforex.kforexutils.history

import arrow.core.Try
import com.dukascopy.api.IHistory
import com.dukascopy.api.Instrument
import com.dukascopy.api.JFException
import com.jforex.kforexutils.price.TickQuote

fun IHistory.latestQuote(instrument: Instrument): Try<TickQuote> =
    Try {
        val tick = getLastTick(instrument)
        if (tick == null) throw(JFException("Latest tick from history for $instrument returned null!"))
        else TickQuote(instrument, tick)
    }