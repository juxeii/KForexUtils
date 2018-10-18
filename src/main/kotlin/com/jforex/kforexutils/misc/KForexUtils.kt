package com.jforex.kforexutils.misc

import com.dukascopy.api.IContext
import com.dukascopy.api.IMessage
import com.jforex.kforexutils.engine.orderMessageGateway
import com.jforex.kforexutils.engine.strategyThread
import com.jforex.kforexutils.message.MessageGateway
import com.jforex.kforexutils.message.MessageToOrderEventType
import com.jforex.kforexutils.order.event.OrderEventGateway
import com.jforex.kforexutils.rx.HotPublisher
import com.jforex.kforexutils.thread.StrategyThread

class KForexUtils(val context: IContext) {
    val strategyThread = StrategyThread(context)
    val engine = context.engine

    private val messagePublisher = HotPublisher<IMessage>()
    val messageGateway = MessageGateway(messagePublisher)
    private val orderEventConverter = MessageToOrderEventType()
    val orderMessageGateway =
        OrderEventGateway(messageGateway.observable, orderEventConverter)

    init {
        engine.strategyThread = strategyThread
        engine.orderMessageGateway = orderMessageGateway
    }
}