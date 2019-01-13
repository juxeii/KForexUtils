package com.jforex.kforexutils.history

import arrow.core.Try
import com.dukascopy.api.IHistory
import com.dukascopy.api.Instrument
import com.dukascopy.api.JFException
import com.jforex.kforexutils.client.platformSettings
import com.jforex.kforexutils.misc.retry
import com.jforex.kforexutils.order.extension.logger
import com.jforex.kforexutils.price.TickQuote
import io.reactivex.Observable

internal fun IHistory.latestQuote(instrument: Instrument) =
    TickQuote(instrument, retry { latestTick(instrument) })

fun IHistory.latestTick(instrument: Instrument) =
    getLastTick(instrument) ?: throw(JFException("Latest tick from history for $instrument returned null!"))

fun <D> IHistory.retry(historyCall: IHistory.() -> D) =
    Observable.fromCallable {
        Try { historyCall.invoke(this) }
            .fold({ throw it }) { it }
    }.retry(
        maxRetry = platformSettings.historyAccessRetries(),
        delayBeforeRetry = platformSettings.historyAccessRetryDelay(),
        doOnRetry = { logger.debug("History call no ${it.second} failed with ${it.first}") }
    ).blockingFirst()