package com.jforex.kforexutils.order

import com.dukascopy.api.IEngine
import com.dukascopy.api.IOrder
import com.jforex.kforexutils.misc.KCallable
import com.jforex.kforexutils.misc.KRunnable
import com.jforex.kforexutils.order.params.OrderCloseParams
import com.jforex.kforexutils.order.params.OrderSLParams
import com.jforex.kforexutils.order.params.OrderSubmitParams
import com.jforex.kforexutils.order.params.OrderTPParams
import com.jforex.kforexutils.thread.StrategyThread
import io.reactivex.Completable
import io.reactivex.Single

class OrderTaskExecutor(private val engine: IEngine, private val strategyThread: StrategyThread)
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

    fun close(orderCloseParams: OrderCloseParams): Completable
    {
        val order = orderCloseParams.order
        val price = orderCloseParams.price
        val amount = orderCloseParams.amount
        return if (price == 0.0) completable { order.close(amount) }
        else completable {
            order.close(
                amount,
                price,
                orderCloseParams.slippage
            )
        }
    }

    fun setSL(orderSLParams: OrderSLParams): Completable
    {
        val order = orderSLParams.order
        return completable {
            order.setStopLossPrice(
                orderSLParams.price,
                orderSLParams.offerSide,
                orderSLParams.trailingStep
            )
        }
    }

    fun setTP(orderTPParams: OrderTPParams) = completable { orderTPParams.order.takeProfitPrice = orderTPParams.price }

    private fun single(callable: KCallable<IOrder>) = strategyThread.observeCallable(callable)

    private fun completable(runner: KRunnable) = strategyThread.observeRunnable(runner)
}