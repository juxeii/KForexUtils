package com.jforex.kforexutils.misc

import com.jforex.kforexutils.settings.PlatformSettings
import org.aeonbits.owner.ConfigFactory

@DslMarker
annotation class OrderDsl

val platformSettings: PlatformSettings = ConfigFactory.create(PlatformSettings::class.java)

val emptyAction: KRunnable = { }
val emptyErrorConsumer: ErrorConsumer = { }
val emptyOrderEventConsumer: OrderEventConsumer = {}

fun thisThreadName(): String = Thread
    .currentThread()
    .name

fun isStrategyThread() = thisThreadName().startsWith(platformSettings.strategyThreadPrefix())