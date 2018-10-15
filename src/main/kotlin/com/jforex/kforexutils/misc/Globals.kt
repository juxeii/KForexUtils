package com.jforex.kforexutils.misc

import com.jforex.kforexutils.settings.PlatformSettings

@DslMarker
annotation class OrderDsl

val emptyAction: KRunnable = { }
val emptyErrorConsumer: ErrorConsumer = { }
val emptyOrderEventConsumer: OrderEventConsumer = {}

fun thisThreadName(): String = Thread
    .currentThread()
    .name

fun isStrategyThread() = thisThreadName().startsWith(PlatformSettings.strategyThreadPrefix)