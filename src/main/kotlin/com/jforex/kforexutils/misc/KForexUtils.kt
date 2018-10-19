package com.jforex.kforexutils.misc

import com.dukascopy.api.IContext
import com.dukascopy.api.IMessage
import com.jakewharton.rxrelay2.PublishRelay
import com.jforex.kforexutils.engine.orderMessageGateway
import com.jforex.kforexutils.engine.taskRunner
import com.jforex.kforexutils.message.MessageGateway
import com.jforex.kforexutils.message.MessageToOrderEvent
import com.jforex.kforexutils.order.OrderTaskRunner
import com.jforex.kforexutils.order.event.OrderEventGateway
import com.jforex.kforexutils.thread.StrategyThread

class KForexUtils(val context: IContext) {
    val strategyThread = StrategyThread(context)
    val engine = context.engine

    private val messagePublisher: PublishRelay<IMessage> = PublishRelay.create()
    val messageGateway = MessageGateway(messagePublisher)
    private val orderEventConverter = MessageToOrderEvent()
    val orderMessageGateway =
        OrderEventGateway(messageGateway.messages, orderEventConverter)
    private val orderTaskRunner = OrderTaskRunner(strategyThread)

    init {
        engine.taskRunner = orderTaskRunner
        engine.orderMessageGateway = orderMessageGateway
    }
}