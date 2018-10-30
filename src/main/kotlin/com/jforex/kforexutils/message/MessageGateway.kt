package com.jforex.kforexutils.message

import com.dukascopy.api.IMessage
import com.jakewharton.rxrelay2.PublishRelay

class MessageGateway(val messages: PublishRelay<IMessage>)
{
    fun onMessage(message: IMessage) = messages.accept(message)
}
