package com.jforex.kforexutils.context

import arrow.effects.DeferredK
import com.dukascopy.api.IContext
import com.jforex.kforexutils.misc.KCallable
import com.jforex.kforexutils.misc.isStrategyThread

fun <T> IContext.deferredTask(callable: KCallable<T>) = DeferredK {
    if (isStrategyThread()) callable()
    else executeTask(callable).get()
}