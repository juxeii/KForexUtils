package com.jforex.kforexutils.message.test

import com.dukascopy.api.IEngine
import com.dukascopy.api.IMessage
import com.dukascopy.api.IOrder
import com.dukascopy.api.Instrument
import com.jforex.kforexutils.order.message.OrderMessageGateway
import com.jforex.kforexutils.order.params.builders.orderSubmitParams
import io.kotlintest.specs.StringSpec
import io.mockk.every
import io.mockk.mockk
import io.reactivex.observers.TestObserver

class OrderMessageGatewayTest : StringSpec()
{
    private val order = mockk<IOrder>()
    private val message = mockk<IMessage>()

    private fun subscribe() = OrderMessageGateway
        .observable()
        .test()

    private fun subscribeAndPublishMessage(): TestObserver<IMessage>
    {
        val testObserver = subscribe()
        //MessageGateway.onMessage(message)
        return testObserver
    }

    init
    {
        "No order message is observed when subscribed after message has been published" {
            every { message.order } returns order
            //MessageGateway.onMessage(message)
            //subscribe().assertNoValues()

            val submitParams = orderSubmitParams(
                label = "hello",
                instrument = Instrument.EURUSD,
                amount = 0.001,
                orderCommand = IEngine.OrderCommand.BUY
            ) {
                price = 1.2345
                submitActions {
                    onFillReject = { println("filled") }
                    basicActions {
                        onStart = { println("zeze") }
                    }
                }

            }
            println("hello i $submitParams")
            println(submitParams.copy(label = "uff"))

        }

        "No order message is observed when message has no order" {
            every { message.order } returns null
            subscribeAndPublishMessage().assertNoValues()
        }

        "After subscription, a published message with an order is filtered and observed" {
            every { message.order } returns order
            //subscribeAndPublishMessage().assertValue(message)
        }
    }
}