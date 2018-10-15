package com.jforex.kforexutils.order.task

import com.jforex.kforexutils.misc.KRunnable
import com.jforex.kforexutils.order.event.consumer.data.OrderEventConsumerData
import com.jforex.kforexutils.order.message.OrderMessageHandler

interface OrderTask {
    fun run(
        orderCall: KRunnable,
        consumerData: OrderEventConsumerData,
        messageHandler: OrderMessageHandler
    )
}