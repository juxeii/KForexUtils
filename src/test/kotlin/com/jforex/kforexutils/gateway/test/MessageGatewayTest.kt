package com.jforex.kforexutils.gateway.test

import com.dukascopy.api.IMessage
import com.jforex.kforexutils.gateway.MessageFilter
import com.jforex.kforexutils.gateway.MessageGateway
import io.kotlintest.specs.StringSpec
import io.mockk.every
import io.mockk.mockk
import io.reactivex.observers.TestObserver

class MessageGatewayTest : StringSpec({
    val message = mockk<IMessage>()
    val messageFilter = mockk<MessageFilter>()

    fun setFilterResult(result: Boolean) = every { messageFilter.isMatch(message) } returns result

    fun subscribeWithFilter() = MessageGateway
        .observable(messageFilter)
        .test()

    fun subscribeAndSendMessage(): TestObserver<IMessage>
    {
        val testObserver = subscribeWithFilter()
        MessageGateway.onMessage(message)
        return testObserver
    }

    MessageGateway.onMessage(message)
    "No message is observed when subscribed after message has been published" {
        setFilterResult(true)
        subscribeWithFilter().assertNoValues()
    }

    "Given subscription and matching filter, message is observed" {
        setFilterResult(true)
        subscribeAndSendMessage().assertValue(message)
    }

    "Given subscription and non matching filter, message is not observed" {
        setFilterResult(false)
        subscribeAndSendMessage().assertNoValues()
    }
})