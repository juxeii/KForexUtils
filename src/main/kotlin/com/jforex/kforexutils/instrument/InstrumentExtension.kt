package com.jforex.kforexutils.instrument

import com.dukascopy.api.ICurrency
import com.dukascopy.api.Instrument
import com.jforex.kforexutils.misc.FieldProperty

val Instrument.noOfDecimalPlaces: Int by FieldProperty { pipScale + 1 }
val Instrument.asNameNoSeparator: String by FieldProperty { toString().replace("/", "") }
val Instrument.currencies: Set<ICurrency> by FieldProperty { setOf(primaryJFCurrency, secondaryJFCurrency) }

operator fun Instrument.contains(currency: ICurrency) = currencies.contains(currency)
