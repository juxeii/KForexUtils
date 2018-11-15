package com.jforex.kforexutils.context

import arrow.core.Try
import arrow.data.ReaderApi
import arrow.data.map
import com.dukascopy.api.IContext
import com.jforex.kforexutils.misc.FieldProperty
import com.jforex.kforexutils.misc.KCallable
import com.jforex.kforexutils.misc.KForexUtils
import com.jforex.kforexutils.settings.PlatformSettings
import com.jforex.kforexutils.thread.isStrategyThread

internal var IContext.platformSettings: PlatformSettings by FieldProperty()

fun <T> IContext.executeTaskBlocking(callable: KCallable<T>) = Try { executeTask(callable).get() }

fun <T> executeTaskOnStrategyThreadBlocking(callable: KCallable<T>) =
    ReaderApi
        .ask<KForexUtils>()
        .map { kForexUtils ->
            with(kForexUtils.context) {
                if (isStrategyThread(platformSettings)) Try { callable() }
                else executeTaskBlocking(callable)
            }
        }
