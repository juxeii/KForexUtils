package com.jforex.kforexutils.order.message

import com.dukascopy.api.IMessage
import com.jforex.kforexutils.message.MessageFilter
import com.jforex.kforexutils.message.MessageGateway
import io.reactivex.Observable

class OrderMessageGateway(private val messageGateway: MessageGateway) {
    val observable: Observable<IMessage> = messageGateway
        .observable
        .filter(MessageFilter.ORDER::isMatch)
}