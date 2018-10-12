package com.jforex.kforexutils.engine.task

import com.jforex.kforexutils.misc.KCallable
import com.jforex.kforexutils.order.Order
import com.jforex.kforexutils.order.event.consumer.data.OrderEventConsumerData
import com.jforex.kforexutils.order.params.actions.OrderBasicActions

interface EngineTask {
    fun executeOnStrategyThread(
        engineCall: KCallable<Order>,
        consumerData: OrderEventConsumerData,
        basicActions: OrderBasicActions
    )
}