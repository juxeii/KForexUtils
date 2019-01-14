package com.jforex.kforexutils.misc

import com.dukascopy.api.Instrument
import com.jforex.kforexutils.order.task.OrderCallHandlers
import com.jforex.kforexutils.price.Price
import io.reactivex.Observable
import io.reactivex.rxkotlin.Observables
import java.math.BigDecimal
import java.util.concurrent.TimeUnit

@DslMarker
annotation class OrderDsl

internal val emptyAction: KRunnable = { }
internal val emptyOrderConsumer: OrderConsumer = {}
internal val emptyErrorConsumer: ErrorConsumer = { }
internal val emptyOrderEventConsumer: OrderEventConsumer = {}
internal val emptyCallHandlers = OrderCallHandlers()

fun Double.toAmount() =
    BigDecimal(this)
        .setScale(2, BigDecimal.ROUND_HALF_UP)
        .toDouble()

fun Double.round(decimalPlaces: Int) =
    BigDecimal(this).setScale(decimalPlaces, BigDecimal.ROUND_HALF_DOWN).toDouble()

fun Double.asPrice(instrument: Instrument) = Price(instrument, this).toDouble()

fun Double.asCost() = round(4)

fun <T> Observable<T>.retry(
    predicate: (Throwable) -> Boolean = { true },
    maxRetry: Long,
    delayBeforeRetry: Long,
    doOnRetry: (p: Pair<Throwable, Long>) -> Unit = { }
): Observable<T> = retryWhen { obs ->
    Observables.zip(
        obs.map { error -> if (predicate(error)) error else throw error },
        Observable.interval(delayBeforeRetry, TimeUnit.MILLISECONDS)
    ).map { pair ->
        doOnRetry(pair)
        if (pair.second >= maxRetry) throw pair.first
    }
}