package com.jforex.kforexutils.order.message

import com.dukascopy.api.IMessage
import com.jforex.kforexutils.message.MessageFilter
import com.jforex.kforexutils.message.MessageGateway
import io.reactivex.Observable

object OrderMessageGateway
{
    fun observable(): Observable<IMessage> =
        MessageGateway
            .observable()
            .filter(MessageFilter.ORDER::isMatch)
}