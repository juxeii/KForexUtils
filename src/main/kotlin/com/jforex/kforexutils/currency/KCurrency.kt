package com.jforex.kforexutils.currency

import arrow.core.Option
import arrow.core.toOption
import com.dukascopy.api.ICurrency
import com.dukascopy.api.Instrument
import com.dukascopy.api.JFCurrency

object KCurrency
{
    fun instanceFromName(currencyName: String): ICurrency = JFCurrency.getInstance(currencyName.toUpperCase())

    fun fromName(currencyName: String): Option<ICurrency>
    {
        val instance = instanceFromName(currencyName)
        return if (instance.isIsoCurrency()) instance.toOption()
        else Option.empty()
    }

    fun fromNames(currencyNames: Collection<String>) = currencyNames
        .map(::fromName)
        .flatMap { it.toList() }
        .toSet()

    fun fromNames(vararg currencyNames: String) = fromNames(currencyNames.toSet())

    fun fromInstrument(instrument: Instrument) = setOf(instrument.primaryJFCurrency, instrument.secondaryJFCurrency)

    fun fromInstrumens(instruments: Collection<Instrument>) = instruments
        .flatMap { fromInstrument(it) }
        .toSet()
}
