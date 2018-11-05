package com.jforex.kforexutils.context

import arrow.core.Try
import com.dukascopy.api.IContext
import com.jforex.kforexutils.misc.FieldProperty
import com.jforex.kforexutils.misc.KCallable
import com.jforex.kforexutils.settings.PlatformSettings
import com.jforex.kforexutils.thread.isStrategyThread

internal var IContext.platformSettings: PlatformSettings by FieldProperty()

fun <T> IContext.executeTaskBlocking(callable: KCallable<T>) = Try { executeTask(callable).get() }
fun <T> IContext.executeTaskOnStrategyThreadBlocking(callable: KCallable<T>) =
    if (isStrategyThread(platformSettings)) Try { callable() }
    else executeTaskBlocking(callable)