package com.jforex.kforexutils.history

import arrow.core.Try
import com.dukascopy.api.IHistory
import com.dukascopy.api.Instrument
import com.dukascopy.api.JFException
import com.jforex.kforexutils.client.platformSettings
import com.jforex.kforexutils.price.TickQuote
import io.reactivex.Observable

internal fun IHistory.latestQuote(instrument: Instrument) =
    historyRetry { latestTick(instrument) }.fold({ throw it }) { TickQuote(instrument, it) }

fun IHistory.latestTick(instrument: Instrument) =
    getLastTick(instrument) ?: throw(JFException("Latest tick from history for $instrument returned null!"))

fun <D> IHistory.historyRetry(historyCall: IHistory.() -> D) =
    Observable.interval(
        0,
        platformSettings.historyAccessRetryDelay(),
        java.util.concurrent.TimeUnit.MILLISECONDS
    )
        .take(platformSettings.historyAccessRetries())
        .map { Try { historyCall.invoke(this) } }
        .takeUntil { it.isSuccess() }
        .blockingFirst()
