package com.jforex.kforexutils.context

import com.dukascopy.api.IContext
import com.jforex.kforexutils.misc.KCallable
import com.jforex.kforexutils.misc.isStrategyThread
import io.reactivex.Single

fun <T> IContext.runOnStrategyThread(callable: KCallable<T>) =
    Single.defer {
        Single.fromCallable {
            if (isStrategyThread()) callable()
            else executeTask(callable).get()
        }
    }

