package com.jforex.kforexutils.thread

import com.jforex.kforexutils.settings.PlatformSettings

fun threadName(thread: Thread): String = thread.name
fun isThread(thread: Thread, name: String) = threadName(thread) == name
fun isStrategyThread(thread: Thread, settings: PlatformSettings) = isThread(thread, settings.strategyThreadPrefix())