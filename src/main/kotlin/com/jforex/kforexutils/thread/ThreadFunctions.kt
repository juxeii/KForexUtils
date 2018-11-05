package com.jforex.kforexutils.thread

import com.jforex.kforexutils.settings.PlatformSettings

fun currentThread(): Thread = Thread.currentThread()
fun threadName(): String = currentThread().name
fun isThreadName(name: String) = threadName() == name
fun isStrategyThread(settings: PlatformSettings) = isThreadName(settings.strategyThreadPrefix())