package com.jforex.kforexutils.order

import com.jforex.kforexutils.order.event.consumer.OrderEventConsumer
import com.jforex.kforexutils.order.event.consumer.data.CloseEventConsumerData
import com.jforex.kforexutils.order.event.consumer.data.SetSLEventConsumerData
import com.jforex.kforexutils.order.event.consumer.data.SetTPEventConsumerData
import com.jforex.kforexutils.order.event.consumer.data.SubmitEventConsumerData
import com.jforex.kforexutils.order.message.OrderMessageHandler
import com.jforex.kforexutils.order.message.OrderMessageHandlerStore
import com.jforex.kforexutils.order.params.OrderCloseParams
import com.jforex.kforexutils.order.params.OrderSLParams
import com.jforex.kforexutils.order.params.OrderSubmitParams
import com.jforex.kforexutils.order.params.OrderTPParams
import io.reactivex.rxkotlin.subscribeBy
import org.apache.logging.log4j.LogManager

class OrderTask(private val taskExecutor: OrderTaskExecutor)
{
    private val logger = LogManager.getLogger(this.javaClass.name)

    fun execute(params: OrderSubmitParams)
    {
        taskExecutor
            .submit(params)
            .subscribeBy(
                onSuccess = {
                    logger.debug("Order created! Now adding the message handler to store")

                    val handler = OrderMessageHandler(it)
                    val messagesConsumerData =
                        SubmitEventConsumerData(params.submitActions)
                    handler.registerConsumer(OrderEventConsumer(messagesConsumerData))
                    OrderMessageHandlerStore.add(handler)
                },
                onError = {
                    logger.debug("Error occured on creating order for submit!")
                    params.submitActions.basicActions.onError(it)
                })
    }

    fun execute(params: OrderCloseParams)
    {
        taskExecutor
            .close(params)
            .subscribeBy(
                onComplete = {
                    logger.debug("Order coming back from closing call.")

                    val messagesConsumerData = CloseEventConsumerData(params.closeActions)
                    val handler = OrderMessageHandlerStore.get(params.order)
                    handler.registerConsumer(OrderEventConsumer(messagesConsumerData))
                },
                onError = {
                    logger.debug("Error occured on closing order")
                    params.closeActions.basicActions.onError(it)
                })
    }

    fun execute(params: OrderSLParams)
    {
        taskExecutor
            .setSL(params)
            .subscribeBy(
                onComplete = {
                    logger.debug("Order coming back from setSL call.")

                    val messagesConsumerData = SetSLEventConsumerData(params.slActions)
                    val handler = OrderMessageHandlerStore.get(params.order)
                    handler.registerConsumer(OrderEventConsumer(messagesConsumerData))
                },
                onError = {
                    logger.debug("Error occured on setting SL")
                    params.slActions.basicActions.onError(it)
                })
    }

    fun execute(params: OrderTPParams)
    {
        taskExecutor
            .setTP(params)
            .subscribeBy(
                onComplete = {
                    logger.debug("Order coming back from setTP call.")

                    val messagesConsumerData = SetTPEventConsumerData(params.tpActions)
                    val handler = OrderMessageHandlerStore.get(params.order)
                    handler.registerConsumer(OrderEventConsumer(messagesConsumerData))
                },
                onError = {
                    logger.debug("Error occured on setting TP")
                    params.tpActions.basicActions.onError(it)
                })
    }
}