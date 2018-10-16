package com.jforex.kforexutils.thread

import com.dukascopy.api.IContext
import com.jforex.kforexutils.misc.KCallable
import com.jforex.kforexutils.misc.KRunnable
import com.jforex.kforexutils.misc.isStrategyThread
import io.reactivex.Completable
import io.reactivex.Single

class StrategyThread(private val context: IContext)
{
    fun observeRunnable(runnable: KRunnable): Completable = observeCallable(runnable).ignoreElement()

    fun <T> observeCallable(callable: KCallable<T>): Single<T> =
        if (isStrategyThread()) Single.fromCallable(callable)
        else Single.defer { Single.fromFuture(context.executeTask(callable)) }
}