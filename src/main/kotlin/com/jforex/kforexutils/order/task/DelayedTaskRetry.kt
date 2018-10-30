package com.jforex.kforexutils.order.task

import com.jakewharton.rxrelay2.BehaviorRelay
import com.jforex.kforexutils.misc.KRunnable
import com.jforex.kforexutils.order.event.OrderEvent
import io.reactivex.Observable
import kotlinx.coroutines.GlobalScope
import java.util.concurrent.TimeUnit

class DelayedTaskRetry(
    private val attempts: Int,
    delayInMs: Long
) : TaskRetry
{
    private var attemptsRelay = BehaviorRelay.createDefault(1)
    private val timer = Observable.timer(delayInMs, TimeUnit.MILLISECONDS)

    override fun onRejectEvent(rejectEvent: OrderEvent, retryCall: KRunnable)
    {
        val attempt = attemptsRelay.blockingFirst()
        if (attempt <= attempts)
        {
            attemptsRelay.accept(attempt + 1)
            GlobalScope.run {
                timer.blockingFirst()
                retryCall.invoke()
            }
        }
    }
}