package com.jforex.kforexutils.message

import com.dukascopy.api.IMessage
import com.jforex.kforexutils.rx.HotPublisher

object MessageGateway
{
    private val messagePublisher = HotPublisher<IMessage>()

    fun onMessage(message: IMessage) = messagePublisher.onNext(message)

    fun observable() = messagePublisher.observable()
}
