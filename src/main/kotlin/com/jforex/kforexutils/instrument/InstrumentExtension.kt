package com.jforex.kforexutils.instrument

import com.dukascopy.api.ICurrency
import com.dukascopy.api.Instrument

val Instrument.currencies
    get() = setOf(primaryJFCurrency, secondaryJFCurrency)

val Instrument.noOfDecimalPlaces
    get() = pipScale + 1

val Instrument.asNameNoSeparator
    get() = toString().replace("/", "")

operator fun Instrument.contains(currency: ICurrency) = currencies.contains(currency)
