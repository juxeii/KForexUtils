package com.jforex.kforexutils.price

import com.dukascopy.api.Instrument
import com.jforex.kforexutils.instrument.noOfDecimalPlaces
import java.math.BigDecimal
import kotlin.math.pow

data class Price(
    val instrument: Instrument,
    private val doubleValue: Double
) {
    val value: BigDecimal = initBigDecimal(doubleValue)
    private val pipScalePrice = initBigDecimal(instrument.pipValue)
    private val scaleFactor = (10.0).pow(instrument.noOfDecimalPlaces - 1)

    private fun initBigDecimal(value: Double) =
        BigDecimal(value).setScale(instrument.noOfDecimalPlaces, BigDecimal.ROUND_HALF_DOWN)

    operator fun plus(pips: Pips) = priceForPips(pips, value::add)

    operator fun minus(pips: Pips) = priceForPips(pips, value::subtract)

    operator fun minus(price: Price): Pips {
        val newPriceValue = value.subtract(price.value)
        return Pips(scaleFactor * newPriceValue.toDouble())
    }

    private fun priceForPips(
        pips: Pips,
        adder: (BigDecimal) -> BigDecimal
    ) = Price(instrument, adder(scalePips(pips)).toDouble())

    private fun scalePips(pips: Pips) = pipScalePrice.multiply(pips.value)
}
