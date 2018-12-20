package com.jforex.kforexutils.history

import arrow.Kind
import arrow.typeclasses.ApplicativeError
import com.dukascopy.api.IHistory
import com.dukascopy.api.Instrument
import com.dukascopy.api.JFException
import com.jforex.kforexutils.price.TickQuote

fun <F> IHistory.latestQuote(instrument: Instrument, AE: ApplicativeError<F, Throwable>): Kind<F, TickQuote> =
    AE.run {
        val tick = getLastTick(instrument)
        if (tick == null) raiseError(JFException("Latest tick from history for $instrument returned null!"))
        else just(TickQuote(instrument, tick))
    }