package com.jforex.kforexutils.instrument

import arrow.core.Option
import arrow.core.toOption
import arrow.syntax.function.memoize
import com.dukascopy.api.ICurrency
import com.dukascopy.api.Instrument
import com.jforex.kforexutils.currency.KCurrency
import com.jforex.kforexutils.misc.MathUtil

object KInstrument
{
    private val memoizeFromCurrencies = { currencyA: ICurrency, currencyB: ICurrency ->
        if (currencyA.equals(currencyB)) Option.empty()
        else memoizeFromName(currencyA.toString() + Instrument.getPairsSeparator() + currencyB.toString())
    }.memoize()

    private val memoizeFromName = { instrumentName: String ->
        val upperCaseName = instrumentName.toUpperCase()
        (if (Instrument.isInverted(upperCaseName)) Instrument.fromInvertedString(upperCaseName)
        else Instrument.fromString(upperCaseName)).toOption()
    }.memoize()

    fun fromName(instrumentName: String) = memoizeFromName(instrumentName)

    fun fromCurrencies(
        currencyA: ICurrency,
        currencyB: ICurrency
    ) = memoizeFromCurrencies(currencyA, currencyB)

    fun fromCombinedCurrencies(currencies: Collection<ICurrency>): Set<Instrument> = MathUtil
        .kPowerSet(currencies, 2)
        .map { ArrayList<ICurrency>(it) }
        .map { memoizeFromCurrencies(it[0], it[1]) }
        .flatMap { it.toList() }
        .toSet()

    fun fromCombinedCurrencyNames(currencyNames: Collection<String>) = fromCombinedCurrencies(currencyNames
        .map(KCurrency::fromName)
        .flatMap { it.toList() }
        .toSet())

    fun fromAnchorCurency(
        anchorCurrency: ICurrency,
        partnerCurrencies: Collection<ICurrency>
    ) = partnerCurrencies
        .map { fromCurrencies(anchorCurrency, it) }
        .flatMap { it.toList() }
        .toSet()
}