package com.jforex.kforexutils.order.task

import com.dukascopy.api.IEngine
import com.dukascopy.api.IOrder
import com.jforex.kforexutils.misc.KCallable
import com.jforex.kforexutils.misc.KRunnable
import com.jforex.kforexutils.order.params.OrderSubmitParams
import com.jforex.kforexutils.thread.StrategyThread
import io.reactivex.Single

class OrderTaskExecutor(
    private val engine: IEngine,
    private val strategyThread: StrategyThread
)
{
    fun submit(orderSubmitParams: OrderSubmitParams): Single<IOrder>
    {
        return single {
            engine.submitOrder(
                orderSubmitParams.label,
                orderSubmitParams.instrument,
                orderSubmitParams.orderCommand,
                orderSubmitParams.amount,
                orderSubmitParams.price,
                orderSubmitParams.slippage,
                orderSubmitParams.stopLossPrice,
                orderSubmitParams.takeProfitPrice,
                orderSubmitParams.goodTillTime,
                orderSubmitParams.comment
            )
        }
    }

    private fun single(callable: KCallable<IOrder>) = strategyThread.observeCallable(callable)

    private fun completable(runner: KRunnable) = strategyThread.observeRunnable(runner)
}