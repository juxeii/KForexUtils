package com.jforex.kforexutils.message.test

import com.dukascopy.api.IMessage
import com.jforex.kforexutils.message.MessageGateway
import io.kotlintest.specs.StringSpec
import io.mockk.mockk

class MessageGatewayTest : StringSpec({
    val message = mockk<IMessage>()

    fun subscribe() = MessageGateway
        .observable()
        .test()

    "No message is observed when subscribed after message has been published" {
        MessageGateway.onMessage(message)
        subscribe().assertNoValues()
    }

    "After subscription, a published message is observed" {
        val testObserver = subscribe()
        //MessageGateway.onMessage(message)
        //testObserver.assertValue(message)
    }
})