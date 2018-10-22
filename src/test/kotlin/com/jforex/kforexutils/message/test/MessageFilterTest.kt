package com.jforex.kforexutils.message.test

import arrow.effects.DeferredK
import arrow.effects.runAsync
import com.dukascopy.api.IContext
import com.dukascopy.api.IMessage
import com.dukascopy.api.IOrder
import com.jforex.kforexutils.message.MessageFilter
import com.jforex.kforexutils.thread.StrategyThread
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.mockk.every
import io.mockk.mockk
import java.util.concurrent.Future

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

    fun assertOnlyThisFilter(filter: MessageFilter) {
        MessageFilter
            .values()
            .forEach {
                if (it == filter)
                    assertMessageMatch(it, true)
                else
                    assertMessageMatch(it, false)
            }
    }

    fun assertForNoOrderFilter(type: IMessage.Type, filter: MessageFilter) {
        setOrderIsPresent(false)
        setTypeOnMessage(type)

        assertOnlyThisFilter(filter)
        assertMessageMatch(MessageFilter.ORDER, false)
    }

    "CALENDAR filter is correct"{
        val context = mockk<IContext>()
        val call = { "HALLO" }
        val fut = mockk<Future<String>>()
        every { context.executeTask<String>(any()) } returns fut
        every { fut.get() } returns call()

        val st = StrategyThread(context)
        st
            .defer(call)
            .runAsync { either ->
                either.fold(
                    { DeferredK { println("Error found ${it.message}") } },
                    { DeferredK { println(it.toString()) } })
            }

        assertForNoOrderFilter(IMessage.Type.CALENDAR, MessageFilter.CALENDAR)
    }

    "CONNECTION filter is correct"{
        assertForNoOrderFilter(IMessage.Type.CONNECTION_STATUS, MessageFilter.CONNECTION)
    }

    "INSTRUMENT filter is correct"{
        assertForNoOrderFilter(IMessage.Type.INSTRUMENT_STATUS, MessageFilter.INSTRUMENT)
    }

    "EMAIL filter is correct"{
        assertForNoOrderFilter(IMessage.Type.MAIL, MessageFilter.EMAIL)
    }

    "NEWS filter is correct"{
        assertForNoOrderFilter(IMessage.Type.NEWS, MessageFilter.NEWS)
    }

    "NOTIFICATION filter is correct"{
        assertForNoOrderFilter(IMessage.Type.NOTIFICATION, MessageFilter.NOTIFICATION)
    }

    "STRATEGY filter is correct"{
        assertForNoOrderFilter(IMessage.Type.STRATEGY_BROADCAST, MessageFilter.STRATEGY)
    }

    "WITHDRAWAL filter is correct"{
        assertForNoOrderFilter(IMessage.Type.WITHDRAWAL, MessageFilter.WITHDRAWAL)
    }

    "ORDER filter is correct"{
        setTypeOnMessage(IMessage.Type.ORDER_CHANGED_OK)
        setOrderIsPresent()

        assertOnlyThisFilter(MessageFilter.ORDER)
    }
})