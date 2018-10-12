package com.jforex.kforexutils.message

import com.dukascopy.api.IMessage
import com.jforex.kforexutils.rx.HotPublisher

class MessageGateway(private val messagePublisher: HotPublisher<IMessage>) {
    val observable = messagePublisher.observable()

    fun onMessage(message: IMessage) = messagePublisher.onNext(message)
}
