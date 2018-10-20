package com.jforex.kforexutils.order.event

import com.dukascopy.api.IMessage
import com.jforex.kforexutils.message.MessageFilter
import com.jforex.kforexutils.message.MessageToOrderEvent
import io.reactivex.Observable

class OrderEventGateway(
    messages: Observable<IMessage>,
    private val messageConverter: MessageToOrderEvent
)
{
    val observable: Observable<OrderEvent> by lazy {
        messages
            .filter(MessageFilter.ORDER::isMatch)
            .map(messageConverter::get)
    }
}