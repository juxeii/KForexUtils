package com.jforex.kforexutils.instrument

import arrow.core.Option
import com.dukascopy.api.ICurrency
import com.dukascopy.api.Instrument
import com.jforex.kforexutils.misc.FieldProperty
import com.jforex.kforexutils.price.Price

val Instrument.noOfDecimalPlaces: Int by FieldProperty { pipScale + 1 }
val Instrument.asNameNoSeparator: String by FieldProperty { toString().replace("/", "") }
val Instrument.currencies: Set<ICurrency> by FieldProperty { setOf(primaryJFCurrency, secondaryJFCurrency) }

operator fun Instrument.contains(currency: ICurrency) = currencies.contains(currency)
operator fun Instrument.contains(currency: Option<ICurrency>) = currency.fold({ false }, { contains(it) })
fun Instrument.forPrice(value: Double) = Price(this, value)