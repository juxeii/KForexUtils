package com.jforex.kforexutils.price

import com.dukascopy.api.IBar
import com.dukascopy.api.Instrument
import com.dukascopy.api.Period

data class BarQuote(
    val instrument: Instrument,
    val period: Period,
    val askBar: IBar,
    val bidBar: IBar
)