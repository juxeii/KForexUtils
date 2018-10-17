package com.jforex.kforexutils.currency

import com.dukascopy.api.ICurrency
import com.dukascopy.api.Instrument
import com.dukascopy.api.JFCurrency
import java.util.*

fun ICurrency.isIsoCurrency() = javaCurrency != null

object Currency {
    fun instanceFromName(currencyName: String): ICurrency = JFCurrency.getInstance(currencyName.toUpperCase())

    fun fromName(currencyName: String): Optional<ICurrency> {
        val instance = instanceFromName(currencyName)
        return if (instance.isIsoCurrency()) Optional.of(instance)
        else Optional.empty()
    }

    fun fromNames(currencyNames: Collection<String>) = currencyNames
        .map { fromName(it) }
        .filter { it.isPresent }
        .map { it.get() }
        .toSet()

    fun fromNames(vararg currencyNames: String) = fromNames(currencyNames.toSet())

    fun fromInstrument(instrument: Instrument) = setOf(instrument.primaryJFCurrency, instrument.secondaryJFCurrency)

    fun fromInstrumens(instruments: Collection<Instrument>) = instruments
        .flatMap { fromInstrument(it) }
        .toSet()
}
