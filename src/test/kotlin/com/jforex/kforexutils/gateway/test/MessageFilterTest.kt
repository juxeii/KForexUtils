package com.jforex.kforexutils.gateway.test

import com.dukascopy.api.IMessage
import com.dukascopy.api.IOrder
import com.jforex.kforexutils.gateway.MessageFilter
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.mockk.every
import io.mockk.mockk

class MessageFilterTest : StringSpec({
    val message = mockk<IMessage>()
    val order = mockk<IOrder>()

    fun setTypeOnMessage(type: IMessage.Type) = every { message.type } returns type

    fun setOrderIsPresent(isPresent: Boolean = true) =
        if (isPresent)
            every { message.order } returns order
        else
            every { message.order } returns null

    fun assertMessageMatch(filter: MessageFilter, result: Boolean) = filter.isMatch(message) shouldBe result

    "Order filter is correct"{
        setTypeOnMessage(IMessage.Type.ORDER_CHANGED_OK)
        setOrderIsPresent()

        assertMessageMatch(MessageFilter.ORDER, true)
        assertMessageMatch(MessageFilter.CONNECTION, false)
        assertMessageMatch(MessageFilter.NOTIFICATION, false)
    }

    "Notification filter is correct"{
        setTypeOnMessage(IMessage.Type.NOTIFICATION)
        setOrderIsPresent(false)

        assertMessageMatch(MessageFilter.NOTIFICATION, true)
        assertMessageMatch(MessageFilter.CONNECTION, false)
        assertMessageMatch(MessageFilter.ORDER, false)
    }

    "Connection filter is correct"{
        setTypeOnMessage(IMessage.Type.CONNECTION_STATUS)
        setOrderIsPresent(false)

        assertMessageMatch(MessageFilter.CONNECTION, true)
        assertMessageMatch(MessageFilter.NOTIFICATION, false)
        assertMessageMatch(MessageFilter.ORDER, false)
    }
})