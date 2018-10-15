package com.jforex.kforexutils.message.test

import com.dukascopy.api.IMessage
import com.dukascopy.api.IOrder
import com.jforex.kforexutils.order.message.OrderMessageGateway
import io.kotlintest.specs.StringSpec
import io.mockk.every
import io.mockk.mockk
import io.reactivex.observers.TestObserver
import io.reactivex.subjects.PublishSubject

class OrderMessageGatewayTest : StringSpec()
{
    private val order = mockk<IOrder>()
    private val message = mockk<IMessage>()
    private val messages: PublishSubject<IMessage> = PublishSubject.create()
    private val orderMessageGateway = OrderMessageGateway(messages)

    private fun subscribe() = orderMessageGateway
        .observable
        .test()

    private fun subscribeAndPublishMessage(): TestObserver<IMessage>
    {
        val testObserver = subscribe()
        messages.onNext(message)
        return testObserver
    }

    init
    {
        "No jfOrder message is observed when subscribed after message has been published" {
            every { message.order } returns order
            messages.onNext(message)
            subscribe().assertNoValues()
        }

        "No jfOrder message is observed when message has no jfOrder" {
            every { message.order } returns null
            subscribeAndPublishMessage().assertNoValues()
        }

        "After subscription, a published message with an jfOrder is filtered and observed" {
            every { message.order } returns order
            subscribeAndPublishMessage().assertValue(message)
        }
    }
}