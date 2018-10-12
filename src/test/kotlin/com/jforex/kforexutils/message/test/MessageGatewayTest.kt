package com.jforex.kforexutils.message.test

import com.dukascopy.api.IMessage
import com.jforex.kforexutils.message.MessageGateway
import com.jforex.kforexutils.rx.HotPublisher
import io.kotlintest.specs.StringSpec
import io.mockk.mockk

class MessageGatewayTest : StringSpec({
    val messagePublisher = HotPublisher<IMessage>()
    val messageGateway = MessageGateway(messagePublisher)
    val message = mockk<IMessage>()

    fun subscribe() = messageGateway
        .observable
        .test()

    fun sendMessage() = messageGateway.onMessage(message)

    "No message is observed when subscribed after message has been published" {
        sendMessage()
        subscribe().assertNoValues()
    }

    "After subscription, a published message is observed" {
        val testObserver = subscribe()
        sendMessage()
        testObserver.assertValue(message)
    }
})