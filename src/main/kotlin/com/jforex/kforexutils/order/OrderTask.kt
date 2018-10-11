package com.jforex.kforexutils.order

import com.jforex.kforexutils.order.event.consumer.OrderEventConsumer
import com.jforex.kforexutils.order.event.consumer.data.SubmitEventConsumerData
import com.jforex.kforexutils.order.message.OrderMessageHandler
import com.jforex.kforexutils.order.message.OrderMessageHandlerStore
import com.jforex.kforexutils.order.params.OrderSubmitParams
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
}