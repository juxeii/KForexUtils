package com.jforex.kforexutils.misc

import com.dukascopy.api.IContext
import com.dukascopy.api.IMessage
import com.jforex.kforexutils.engine.orderCreation
import com.jforex.kforexutils.engine.task.EngineOrderCreationImpl
import com.jforex.kforexutils.message.MessageGateway
import com.jforex.kforexutils.order.message.OrderMessageGateway
import com.jforex.kforexutils.rx.HotPublisher
import com.jforex.kforexutils.thread.StrategyThread

class ComponentFactory(val context: IContext)
{
    val strategyThread = StrategyThread(context)
    val engine = context.engine

    private val messagePublisher = HotPublisher<IMessage>()
    val messageGateway = MessageGateway(messagePublisher)
    val orderMessageGateway = OrderMessageGateway(messageGateway.observable)
    private val orderCreationImpl = EngineOrderCreationImpl(
        strategyThread = strategyThread,
        orderMessageGateway = orderMessageGateway
    )

    init
    {
        engine.orderCreation = orderCreationImpl
    }
}