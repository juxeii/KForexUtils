package com.jforex.kforexutils.order.event.test

import com.dukascopy.api.IOrder
import com.jakewharton.rxrelay2.PublishRelay
import com.jforex.kforexutils.misc.KForexUtils
import com.jforex.kforexutils.order.event.OrderEvent
import com.jforex.kforexutils.order.event.OrderEventType
import com.jforex.kforexutils.order.event.OrderEventsConfiguration
import com.jforex.kforexutils.order.event.subscribeToOrderEvents
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.mockk.every
import io.mockk.mockk

class OrderEventFunctionsTest : StringSpec({
    val kForexUtils = mockk<KForexUtils>()
    val order = mockk<IOrder>()
    val orderEvents: PublishRelay<OrderEvent> = PublishRelay.create()
    var handlerWasInvoked = false
    var completionWasInvoked = false
    val configParams = OrderEventsConfiguration(
        order = order,
        handlers = mapOf(OrderEventType.CHANGED_AMOUNT to { (_) -> handlerWasInvoked = true }),
        finishTypes = setOf(OrderEventType.CHANGED_GTT),
        completionCall = { completionWasInvoked = true }
    )

    fun sendOrderEvent(
        order: IOrder,
        type: OrderEventType = OrderEventType.CHANGED_AMOUNT
    )
    {
        handlerWasInvoked = false
        completionWasInvoked = false
        val orderEvent = OrderEvent(order = order, type = type)
        orderEvents.accept(orderEvent)
    }

    every { kForexUtils.orderEvents }.returns(orderEvents)
    "Not related orders are filtered" {
        subscribeToOrderEvents(configParams).run(kForexUtils)
        sendOrderEvent(mockk())

        handlerWasInvoked shouldBe false
    }

    "Related orders are not filtered" {
        subscribeToOrderEvents(configParams).run(kForexUtils)
        sendOrderEvent(order)

        handlerWasInvoked shouldBe true
    }

    "Event type is filtered if no handler is given" {
        subscribeToOrderEvents(configParams).run(kForexUtils)
        sendOrderEvent(order, OrderEventType.CHANGED_GTT)

        handlerWasInvoked shouldBe false
    }

    "Completion call is invoked for finish event type" {
        subscribeToOrderEvents(configParams).run(kForexUtils)
        sendOrderEvent(order, OrderEventType.CHANGED_GTT)

        completionWasInvoked shouldBe true
    }

    "Completion call is not invoked for non-finishing event type" {
        subscribeToOrderEvents(configParams).run(kForexUtils)
        sendOrderEvent(order)

        completionWasInvoked shouldBe false
    }
})