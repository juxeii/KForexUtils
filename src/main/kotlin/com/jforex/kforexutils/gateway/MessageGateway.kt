package com.jforex.kforexutils.gateway

import com.dukascopy.api.IMessage
import com.jforex.kforexutils.rx.HotPublisher
import io.reactivex.Observable

object MessageGateway
{
    private val messagePublisher = HotPublisher<IMessage>()

    fun onMessage(message: IMessage) = messagePublisher.onNext(message)

    fun observable(messageFilter: MessageFilter): Observable<IMessage> = messagePublisher
        .observable()
        .filter(messageFilter::isMatch)
}
