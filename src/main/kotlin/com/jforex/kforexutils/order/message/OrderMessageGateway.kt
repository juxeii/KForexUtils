package com.jforex.kforexutils.order.message

import com.dukascopy.api.IMessage
import com.jforex.kforexutils.message.MessageFilter
import com.jforex.kforexutils.message.MessageGateway
import com.jforex.kforexutils.order.OrderStore
import io.reactivex.Observable

class OrderMessageGateway(
    private val messageGateway: MessageGateway,
    private val orderStore: OrderStore
)
{
    val observable: Observable<IMessage> = messageGateway
        .observable
        .filter(MessageFilter.ORDER::isMatch)
        .filter { orderStore.contains(it.order) }
}