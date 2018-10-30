package com.jforex.kforexutils.context

import com.dukascopy.api.IContext
import com.jforex.kforexutils.misc.KCallable
import com.jforex.kforexutils.misc.isStrategyThread

fun <T> IContext.runOnStrategyThread(callable: KCallable<T>): T =
    if (isStrategyThread()) callable()
    else executeTask(callable).get()

