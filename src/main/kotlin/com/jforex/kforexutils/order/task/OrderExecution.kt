package com.jforex.kforexutils.order.task

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.misc.KRunnable
import com.jforex.kforexutils.order.event.consumer.data.OrderEventConsumerData
import com.jforex.kforexutils.order.params.actions.OrderBasicActions

interface OrderExecution {
    fun executeOnStrategyThread(
        orderCall: KRunnable,
        order: IOrder,
        consumerData: OrderEventConsumerData,
        basicActions: OrderBasicActions
    )
}