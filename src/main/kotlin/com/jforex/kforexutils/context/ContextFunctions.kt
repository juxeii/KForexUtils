package com.jforex.kforexutils.context

import arrow.core.Try
import arrow.data.ReaderApi
import arrow.data.map
import com.jforex.kforexutils.misc.KCallable
import com.jforex.kforexutils.misc.KForexUtils
import com.jforex.kforexutils.thread.isStrategyThread

internal fun <T> executeTaskOnStrategyThreadBlocking(callable: KCallable<T>) =
    ReaderApi
        .ask<KForexUtils>()
        .map { kForexUtils ->
            with(kForexUtils.context) {
                Try {
                    if (isStrategyThread(kForexUtils.platformSettings)) callable()
                    else executeTask(callable).get()
                }
            }
        }
