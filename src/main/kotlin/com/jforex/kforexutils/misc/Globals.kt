package com.jforex.kforexutils.misc

import com.jforex.kforexutils.settings.PlatformSettings

@DslMarker
annotation class OrderDsl

lateinit var KForexUtils: KForexUtilsSingleton

val emptyAction: KRunnable = { }
val emptyOrderEventConsumer: OrderEventConsumer = { }
val emptyErrorConsumer: ErrorConsumer = { }

fun thisThreadName(): String = Thread
    .currentThread()
    .name

fun isStrategyThread() = thisThreadName().startsWith(PlatformSettings.strategyThreadPrefix)