package com.jforex.kforexutils.price

import java.math.BigDecimal

data class Pips(private val doubleValue: Double) {
    val value: BigDecimal = BigDecimal(doubleValue).setScale(1, BigDecimal.ROUND_HALF_DOWN)
}