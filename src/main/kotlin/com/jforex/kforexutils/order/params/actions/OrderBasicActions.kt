package com.jforex.kforexutils.order.params.actions

import arrow.core.Option
import com.jforex.kforexutils.misc.ErrorConsumer
import com.jforex.kforexutils.misc.KRunnable
import com.jforex.kforexutils.misc.emptyAction
import com.jforex.kforexutils.misc.emptyErrorConsumer
import io.reactivex.Observable

data class OrderBasicActions(
    val onStart: KRunnable = emptyAction,
    val onComplete: KRunnable = emptyAction,
    val onError: ErrorConsumer = emptyErrorConsumer,
    val retryObservable: Option<Observable<Long>> = Option.empty()
)