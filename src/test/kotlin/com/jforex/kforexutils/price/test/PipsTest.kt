package com.jforex.kforexutils.price.test

import com.jforex.kforexutils.price.Pips
import io.kotlintest.data.forall
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

class PipsTest : StringSpec() {
    private fun assertPips(
        initValue: Double,
        expectedValue: Double
    ) {
        val pips = Pips(initValue)
        pips.value.toDouble() shouldBe expectedValue
    }

    init {
        "Rounding is correct" {
            forall(
                row(4.1, 4.1),
                row(4.15, 4.2),
                row(4.18, 4.2),
                row(4.12, 4.1),
                row(4.1533333, 4.2)
            ) { initValue, expectedValue ->
                assertPips(initValue, expectedValue)
            }
        }
    }
}