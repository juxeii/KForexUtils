package com.jforex.kforexutils.misc


@DslMarker
annotation class OrderDsl

//val platformSettings: PlatformSettings = ConfigFactory.create(PlatformSettings::class.java)

val emptyAction: KRunnable = { }
val emptyOrderConsumer: OrderConsumer = {}
val emptyErrorConsumer: ErrorConsumer = { }
val emptyOrderEventConsumer: OrderEventConsumer = {}

fun thisThreadName(): String = Thread
    .currentThread()
    .name
