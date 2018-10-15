package com.jforex.kforexutils.misc

import com.dukascopy.api.IContext
import com.dukascopy.api.IMessage
import com.jforex.kforexutils.engine.orderCreation
import com.jforex.kforexutils.engine.task.EngineOrderCreationImpl
import com.jforex.kforexutils.message.MessageGateway
import com.jforex.kforexutils.order.OrderStore
import com.jforex.kforexutils.order.message.OrderMessageGateway
import com.jforex.kforexutils.rx.HotPublisher
import com.jforex.kforexutils.thread.StrategyThread

class ComponentFactory(val context: IContext)
{
    val strategyThread = StrategyThread(context)
    val engine = context.engine
    val orderStore = OrderStore()

    private val messagePublisher = HotPublisher<IMessage>()
    val messageGateway = MessageGateway(messagePublisher)
    val orderMessageGateway = OrderMessageGateway(messageGateway, orderStore)
    private val orderCreationImpl = EngineOrderCreationImpl(
        strategyThread,
        orderStore,
        orderMessageGateway
    )
    init
    {
        engine.orderCreation = orderCreationImpl
    }
}