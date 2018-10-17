package com.jforex.kforexutils.price

import com.dukascopy.api.Instrument
import java.math.BigDecimal

data class Price(
    val instrument: Instrument,
    private val doubleValue: Double
) {
    val value: BigDecimal = BigDecimal(doubleValue).setScale(instrument.pipScale + 1, BigDecimal.ROUND_HALF_DOWN)
    private val pipScalePrice =
        BigDecimal(instrument.pipValue).setScale(instrument.pipScale + 1, BigDecimal.ROUND_HALF_DOWN)

    operator fun plus(pips: Pips): Price {
        return Price(instrument, value.add(scalePips(pips)).toDouble())
    }

    private fun scalePips(pips: Pips) = pipScalePrice.multiply(pips.value)
}
