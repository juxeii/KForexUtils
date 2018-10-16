package com.jforex.kforexutils.order.message

import com.dukascopy.api.IMessage
import com.jforex.kforexutils.message.MessageFilter
import com.jforex.kforexutils.message.MessageToOrderEventType
import com.jforex.kforexutils.order.event.OrderEvent
import io.reactivex.Observable

class OrderEventGateway(
    private val messages: Observable<IMessage>,
    private val messageConverter: MessageToOrderEventType
) {
    val observable: Observable<OrderEvent> = messages
        .filter(MessageFilter.ORDER::isMatch)
        .map { messageToOrderEvent(it) }

    private fun messageToOrderEvent(orderMessage: IMessage): OrderEvent {
        val eventType = messageConverter.convert(orderMessage)
        return OrderEvent(orderMessage.order, eventType)
    }
}