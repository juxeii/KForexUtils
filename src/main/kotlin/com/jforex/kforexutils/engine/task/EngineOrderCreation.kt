package com.jforex.kforexutils.engine.task

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.misc.KCallable
import com.jforex.kforexutils.order.event.consumer.data.OrderEventConsumerData

interface EngineOrderCreation
{
    fun run(
        engineCall: KCallable<IOrder>,
        consumerData: OrderEventConsumerData
    )
}