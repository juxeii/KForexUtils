package com.jforex.kforexutils.engine.task

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.misc.KCallable
import com.jforex.kforexutils.order.event.consumer.data.OrderEventConsumerData
import com.jforex.kforexutils.order.params.actions.OrderBasicActions

interface EngineOrderCreation
{
    fun createOnStrategyThread(
        engineCall: KCallable<IOrder>,
        consumerData: OrderEventConsumerData,
        basicActions: OrderBasicActions
    )
}