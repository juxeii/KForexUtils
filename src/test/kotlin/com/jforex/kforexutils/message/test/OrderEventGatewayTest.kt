package com.jforex.kforexutils.message.test

import com.dukascopy.api.IMessage
import com.dukascopy.api.IOrder
import com.jforex.kforexutils.message.MessageGateway
import com.jforex.kforexutils.message.OrderEventGateway
import io.kotlintest.specs.StringSpec
import io.mockk.every
import io.mockk.mockk

class OrderEventGatewayTest : StringSpec({
    val order = mockk<IOrder>()
    val message = mockk<IMessage>()

    fun subscribe() = OrderEventGateway
        .observable()
        .test()

    "No order event is observed when subscribed after message has been published" {
        every { message.order } returns order
        MessageGateway.onMessage(message)
        subscribe().assertNoValues()
    }

    "No order event is observed when message has no order" {
        every { message.order } returns null
        val testObserver = subscribe()
        MessageGateway.onMessage(message)
        testObserver.assertNoValues()
    }

    "After subscription, a published message is converted to an order event and observed" {
        val testObserver = subscribe()
        every { message.order } returns order
        MessageGateway.onMessage(message)
        testObserver.assertValueCount(1)
    }
})