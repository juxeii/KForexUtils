package com.jforex.kforexutils.misc

import com.jforex.kforexutils.settings.PlatformSettings

@DslMarker
annotation class OrderDsl

lateinit var KForexUtils: KForexUtilsSingleton
val engine = KForexUtils.engine
val strategyThread = KForexUtils.strategyThread
val taskExecutor = KForexUtils.orderTaskExecutor

val emptyAction: KRunnable = { }
val emptyOrderEventConsumer: OrderEventConsumer = { }
val emptyErrorConsumer: ErrorConsumer = { }

fun thisThreadName(): String = Thread
    .currentThread()
    .name

fun isStrategyThread() = thisThreadName().startsWith(PlatformSettings.strategyThreadPrefix)

fun <T> executeOnStrategyThread(callable: KCallable<T>) = strategyThread.observeCallable(callable)