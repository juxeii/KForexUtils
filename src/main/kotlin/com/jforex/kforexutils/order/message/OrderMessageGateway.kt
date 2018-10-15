package com.jforex.kforexutils.order.message

import com.dukascopy.api.IMessage
import com.jforex.kforexutils.message.MessageFilter
import io.reactivex.Observable

class OrderMessageGateway(private val messages: Observable<IMessage>)
{
    val observable: Observable<IMessage> = messages.filter(MessageFilter.ORDER::isMatch)
}