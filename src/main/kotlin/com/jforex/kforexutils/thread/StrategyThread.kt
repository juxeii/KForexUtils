package com.jforex.kforexutils.thread

import arrow.effects.DeferredK
import com.dukascopy.api.IContext
import com.jforex.kforexutils.misc.KCallable
import com.jforex.kforexutils.misc.isStrategyThread

class StrategyThread(private val context: IContext)
{
    fun <T> defer(callable: KCallable<T>) = DeferredK {
        if (isStrategyThread()) callable()
        else context.executeTask(callable).get()
    }
}