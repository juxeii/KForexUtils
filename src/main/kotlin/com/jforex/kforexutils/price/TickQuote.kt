package com.jforex.kforexutils.price

import com.dukascopy.api.ITick
import com.dukascopy.api.Instrument

data class TickQuote(
    val instrument: Instrument,
    val tick: ITick
)