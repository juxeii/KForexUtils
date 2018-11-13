package com.jforex.kforexutils.misc

import com.dukascopy.api.IContext
import com.dukascopy.api.IMessage
import com.dukascopy.api.IOrder
import com.google.common.collect.MapMaker
import com.jakewharton.rxrelay2.PublishRelay
import com.jforex.kforexutils.engine.kForexUtils
import com.jforex.kforexutils.message.MessageGateway
import com.jforex.kforexutils.message.MessageToOrderEventType
import com.jforex.kforexutils.order.event.OrderEventGateway
import com.jforex.kforexutils.order.event.handler.data.OrderEventHandlerData
import com.jforex.kforexutils.settings.PlatformSettings
import org.aeonbits.owner.ConfigFactory

class KForexUtils(val context: IContext) {
    val engine = context.engine
    private val messagePublisher: PublishRelay<IMessage> = PublishRelay.create()
    val messageGateway = MessageGateway(messagePublisher)
    private val orderEventConverter = MessageToOrderEventType()
    val orderMessageGateway = OrderEventGateway(
        messageGateway.messages,
        orderEventConverter
    )
    val platformSettings: PlatformSettings = ConfigFactory.create(PlatformSettings::class.java)
    val orderEventHandlers = MapMaker()
        .weakKeys()
        .makeMap<IOrder, List<OrderEventHandlerData>>()

    init {
        engine.kForexUtils = this
    }
}
