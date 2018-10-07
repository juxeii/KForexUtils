package com.jforex.kforexutils.message

import com.dukascopy.api.IMessage
import com.jforex.kforexutils.rx.HotPublisher

object OrderEventGateway
{
    private val messages = MessageGateway.observable()
    private val orderEvents = HotPublisher<OrderEvent>()

    init
    {
        messages
            .filter(MessageFilter.ORDER::isMatch)
            .subscribe(this::onOrderMessage)
    }

    private fun onOrderMessage(orderMessage: IMessage)
    {
        val orderEvent = MessageToOrderEvent.convert(orderMessage)
        orderEvents.onNext(orderEvent)
    }

    fun observable() = orderEvents.observable()
}