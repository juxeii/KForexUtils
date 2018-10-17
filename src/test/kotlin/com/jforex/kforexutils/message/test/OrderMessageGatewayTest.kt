package com.jforex.kforexutils.message.test

import com.dukascopy.api.IMessage
import com.dukascopy.api.IOrder
import com.jforex.kforexutils.message.MessageToOrderEventType
import com.jforex.kforexutils.order.event.OrderEvent
import com.jforex.kforexutils.order.event.OrderEventGateway
import com.jforex.kforexutils.order.event.OrderEventType
import io.kotlintest.specs.StringSpec
import io.mockk.every
import io.mockk.mockk
import io.reactivex.observers.TestObserver
import io.reactivex.subjects.PublishSubject

class OrderMessageGatewayTest : StringSpec() {
    private val order = mockk<IOrder>()
    private val message = mockk<IMessage>()
    private val messages: PublishSubject<IMessage> = PublishSubject.create()
    private val messageConverter = mockk<MessageToOrderEventType>()
    private val orderMessageGateway = OrderEventGateway(messages, messageConverter)

    private fun subscribe() = orderMessageGateway
        .observable
        .test()

    private fun subscribeAndPublishMessage(): TestObserver<OrderEvent> {
        val testObserver = subscribe()
        messages.onNext(message)
        return testObserver
    }

    init {
        every { messageConverter.convert(message) } returns OrderEventType.CHANGED_AMOUNT
        "No order event is observed when subscribed after message has been published" {
            every { message.order } returns order
            messages.onNext(message)
            subscribe().assertNoValues()
        }

        "No order event is observed when message has no order" {
            every { message.order } returns null
            subscribeAndPublishMessage().assertNoValues()
        }

        "After subscription, a published message with an order event is filtered and observed" {
            every { message.order } returns order
            subscribeAndPublishMessage().assertValue {
                it.order == order
                it.type == OrderEventType.CHANGED_AMOUNT
            }
        }
    }
}