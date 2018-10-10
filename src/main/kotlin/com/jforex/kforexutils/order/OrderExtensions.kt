package com.jforex.kforexutils.order

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.misc.KForexUtils
import com.jforex.kforexutils.misc.KRunnable
import com.jforex.kforexutils.order.event.consumer.OrderEventConsumer
import com.jforex.kforexutils.order.event.consumer.data.SetTPEventConsumerData
import com.jforex.kforexutils.order.message.OrderMessageHandlerStore
import com.jforex.kforexutils.order.params.actions.builders.OrderTPActionsBuilder
import io.reactivex.rxkotlin.subscribeBy
import org.apache.logging.log4j.LogManager

private val logger = LogManager.getLogger()

fun IOrder.isOpened() = state == IOrder.State.OPENED
fun IOrder.isFilled() = state == IOrder.State.FILLED
fun IOrder.isClosed() = state == IOrder.State.CLOSED
fun IOrder.isCanceled() = state == IOrder.State.CANCELED
fun IOrder.isConditional() = orderCommand.isConditional

fun IOrder.setTP(price: Double, block: OrderTPActionsBuilder.() -> Unit)
{
    val tpActions = OrderTPActionsBuilder()
        .apply(block)
        .build()
    completable {
        ->
        logger.debug("Init completable")
        takeProfitPrice = price
    }
        .subscribeBy(
            onComplete = {
                logger.debug("Order coming back from setTP call.")

                val messagesConsumerData = SetTPEventConsumerData(tpActions)
                val handler = OrderMessageHandlerStore.get(this)
                handler.registerConsumer(OrderEventConsumer(messagesConsumerData))
            },
            onError = {
                logger.debug("Error occured on setting TP")
                tpActions.basicActions.onError(it)
            })
}


private val strategyThread = KForexUtils.strategyThread
private val taskExecutor = KForexUtils.orderTaskExecutor
private fun completable(runner: KRunnable) = strategyThread.observeRunnable(runner)