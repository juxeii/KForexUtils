package com.jforex.kforexutils.thread

import arrow.data.ReaderApi
import arrow.data.map
import com.jforex.kforexutils.misc.KCallable
import com.jforex.kforexutils.misc.KForexUtils
import com.jforex.kforexutils.settings.PlatformSettings

fun currentThread() = Thread.currentThread()
fun threadName(): String = currentThread().name
fun isThreadName(name: String) = threadName() == name
fun isStrategyThread(settings: PlatformSettings) = isThreadName(settings.strategyThreadPrefix())

fun <T> runOnStrategyThread(callable: KCallable<T>) =
    ReaderApi
        .ask<KForexUtils>()
        .map { utils ->
            if (isStrategyThread(utils.platformSettings)) callable()
            else utils.context.executeTask(callable).get()
        }