package com.jforex.kforexutils.order.event

import com.dukascopy.api.IOrder

interface IOrderEvent
{
    val order: IOrder
    val type: OrderEventType
}

class OrderEvent(
    override val order: IOrder,
    override val type: OrderEventType
) : IOrderEvent

abstract class OrderSubmitEvent : IOrderEvent
abstract class OrderMergeEvent : IOrderEvent
abstract class OrderCloseEvent : IOrderEvent
abstract class OrderTPEvent : IOrderEvent
abstract class OrderSLEvent : IOrderEvent
abstract class OrderLabelEvent : IOrderEvent
abstract class OrderCommentEvent : IOrderEvent
abstract class OrderOpenPriceEvent : IOrderEvent
abstract class OrderGTTEvent : IOrderEvent
abstract class OrderAmountEvent : IOrderEvent
