package com.jforex.kforexutils.misc

import com.dukascopy.api.IContext
import com.dukascopy.api.IMessage
import com.jakewharton.rxrelay2.PublishRelay
import com.jforex.kforexutils.engine.orderMessageGateway
import com.jforex.kforexutils.engine.taskRunner
import com.jforex.kforexutils.message.MessageGateway
import com.jforex.kforexutils.message.MessageToOrderEventType
import com.jforex.kforexutils.order.event.OrderEventGateway
import com.jforex.kforexutils.order.task.OrderTaskRunner
import com.jforex.kforexutils.settings.PlatformSettings
import org.aeonbits.owner.ConfigFactory

class KForexUtils(val context: IContext)
{
    private val orderTaskRunner = OrderTaskRunner(context)
    val engine = context.engine
    private val messagePublisher: PublishRelay<IMessage> = PublishRelay.create()
    val messageGateway = MessageGateway(messagePublisher)
    private val orderEventConverter = MessageToOrderEventType()
    private val orderMessageGateway = OrderEventGateway(
        messageGateway.messages,
        orderEventConverter
    )
    val platformSettings: PlatformSettings = ConfigFactory.create(PlatformSettings::class.java)

    init
    {
        engine.taskRunner = orderTaskRunner
        engine.orderMessageGateway = orderMessageGateway
    }
}