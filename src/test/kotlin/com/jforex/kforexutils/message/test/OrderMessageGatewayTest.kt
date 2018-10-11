package com.jforex.kforexutils.message.test

import com.dukascopy.api.IMessage
import com.dukascopy.api.IOrder
import com.jforex.kforexutils.message.MessageGateway
import com.jforex.kforexutils.order.message.OrderMessageGateway
import io.kotlintest.specs.StringSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.reactivex.observers.TestObserver

class OrderMessageGatewayTest : StringSpec()
{
    private val order = spyk(mockk<IOrder>())
    private val message = mockk<IMessage>()

    private fun subscribe() = OrderMessageGateway
        .observable()
        .test()

    private fun subscribeAndPublishMessage(): TestObserver<IMessage>
    {
        val testObserver = subscribe()
        MessageGateway.onMessage(message)
        return testObserver
    }

    init
    {
        "No jfOrder message is observed when subscribed after message has been published" {
            every { message.order } returns order
            MessageGateway.onMessage(message)
            subscribe().assertNoValues()

            order.setTP(1.1234) {
                onReject = {}
            }

        }

        "No jfOrder message is observed when message has no jfOrder" {
            every { message.order } returns null
            //subscribeAndPublishMessage().assertNoValues()
        }

        "After subscription, a published message with an jfOrder is filtered and observed" {
            every { message.order } returns order
            subscribeAndPublishMessage().assertValue(message)
        }
    }
}