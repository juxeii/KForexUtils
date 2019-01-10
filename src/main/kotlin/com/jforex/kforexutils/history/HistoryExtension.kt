package com.jforex.kforexutils.history

import arrow.core.Failure
import arrow.core.Success
import arrow.core.Try
import com.dukascopy.api.IHistory
import com.dukascopy.api.Instrument
import com.dukascopy.api.JFException
import com.jforex.kforexutils.client.platformSettings
import com.jforex.kforexutils.order.extension.logger
import com.jforex.kforexutils.price.TickQuote
import io.reactivex.Observable

internal fun IHistory.latestQuote(instrument: Instrument) =
    TickQuote(instrument, retry { latestTick(instrument) })

fun IHistory.latestTick(instrument: Instrument) =
    getLastTick(instrument) ?: throw(JFException("Latest tick from history for $instrument returned null!"))

fun <D> IHistory.retry(historyCall: IHistory.() -> D) =
    Observable.interval(
        0,
        platformSettings.historyAccessRetryDelay(),
        java.util.concurrent.TimeUnit.MILLISECONDS
    )
        .take(platformSettings.historyAccessRetries())
        .map { tryNumber ->
            Try { historyCall.invoke(this) }
                .fold({
                    logger.debug("History call no $tryNumber failed with $it")
                    Failure(it)
                })
                { Success(it) }
        }
        .takeUntil { it.isSuccess() }
        .map { tickTry -> tickTry.fold({ throw it }) { it } }
        .blockingFirst()
