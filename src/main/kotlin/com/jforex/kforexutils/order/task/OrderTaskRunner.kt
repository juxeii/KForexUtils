package com.jforex.kforexutils.order.task

import com.dukascopy.api.IContext
import com.dukascopy.api.IOrder
import com.jforex.kforexutils.context.runOnStrategyThread
import com.jforex.kforexutils.misc.KCallable
import io.reactivex.rxkotlin.subscribeBy

class OrderTaskRunner(private val context: IContext) {

    fun run(
        task: KCallable<IOrder>,
        actions: OrderTaskActions
    ) = with(actions) {
        context
            .runOnStrategyThread(task)
            .doOnSubscribe { onStart() }
            .subscribeBy(
                onSuccess = { onSuccess(it) },
                onError = { onError(it) })
    }
}