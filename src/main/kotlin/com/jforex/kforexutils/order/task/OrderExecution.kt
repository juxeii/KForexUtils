package com.jforex.kforexutils.order.task

import com.jforex.kforexutils.misc.KRunnable
import com.jforex.kforexutils.order.Order
import com.jforex.kforexutils.order.event.consumer.data.OrderEventConsumerData
import com.jforex.kforexutils.order.params.actions.OrderBasicActions

interface OrderExecution {
    fun executeOnStrategyThread(
        orderCall: KRunnable,
        order: Order,
        consumerData: OrderEventConsumerData,
        basicActions: OrderBasicActions
    )
}