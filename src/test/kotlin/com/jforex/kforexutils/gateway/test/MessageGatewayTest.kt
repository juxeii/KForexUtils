package com.jforex.kforexutils.gateway.test

import com.dukascopy.api.IMessage
import com.jforex.kforexutils.gateway.MessageGateway
import io.kotlintest.specs.StringSpec
import io.mockk.mockk

class MessageGatewayTests : StringSpec({

    "MessageGateway should publish message"{
        val newMessage = mockk<IMessage>()
        MessageGateway.onMessage(newMessage)
    }
})