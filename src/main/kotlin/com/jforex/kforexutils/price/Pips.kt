package com.jforex.kforexutils.price

import java.math.BigDecimal

data class Pips(private val doubleValue: Double) {
    val value: BigDecimal by lazy { BigDecimal(doubleValue).setScale(1, BigDecimal.ROUND_HALF_DOWN) }

    operator fun plus(pips: Pips) = genericAdd(pips, value::add)

    operator fun minus(pips: Pips) = genericAdd(pips, value::subtract)

    fun toDouble() = value.toDouble()

    private fun genericAdd(
        pips: Pips,
        adder: (BigDecimal) -> BigDecimal
    ) = Pips(adder(pips.value).toDouble())
}