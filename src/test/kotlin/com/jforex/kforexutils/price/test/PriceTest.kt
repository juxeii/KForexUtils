package com.jforex.kforexutils.price.test

import com.dukascopy.api.Instrument
import com.jforex.kforexutils.price.Pips
import com.jforex.kforexutils.price.Price
import io.kotlintest.data.forall
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

class PriceTest : StringSpec() {
    private fun assertPrice(
        instrument: Instrument,
        initValue: Double,
        expectedValue: Double
    ) {
        val price = Price(instrument, initValue)
        price.value.toDouble() shouldBe expectedValue
    }

    private fun assertPipsAdded(
        instrument: Instrument,
        initPriceValue: Double,
        pips: Double,
        expectedPrice: Double
    ) {
        val initPrice = Price(instrument, initPriceValue)
        val newPrice = initPrice + Pips(pips)
        newPrice.value.toDouble() shouldBe expectedPrice
    }

    init {
        "Rounding is correct EURUSD" {
            forall(
                row(1.15888, 1.15888),
                row(1.158889, 1.15889),
                row(1.158881, 1.15888),
                row(1.1588853223232, 1.15889)
            ) { initValue, expectedValue ->
                assertPrice(
                    Instrument.EURUSD,
                    initValue,
                    expectedValue
                )
            }
        }

        "Rounding is correct EURJPY" {
            forall(
                row(132.453, 132.453),
                row(132.4555, 132.456),
                row(132.4551, 132.455),
                row(132.45345333, 132.453)
            ) { initValue, expectedValue ->
                assertPrice(
                    Instrument.EURJPY,
                    initValue,
                    expectedValue
                )
            }
        }

        "Adding pips is correct EURUSD" {
            forall(
                row(1.15888, 4.345, 1.15931)
            ) { initValue, pips, expectedValue ->
                assertPipsAdded(
                    Instrument.EURUSD,
                    initValue,
                    pips,
                    expectedValue
                )
            }
        }
    }
}