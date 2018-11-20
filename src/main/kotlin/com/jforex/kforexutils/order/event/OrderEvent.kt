package com.jforex.kforexutils.order.event

import com.dukascopy.api.IOrder

interface IOrderEvent {
    val order: IOrder
    val type: OrderEventType
}

data class OrderEvent(
    override val order: IOrder,
    override val type: OrderEventType
) : IOrderEvent

data class OrderSubmitEvent(
    override val order: IOrder,
    override val type: OrderEventType
) : IOrderEvent

data class OrderMergeEvent(
    override val order: IOrder,
    override val type: OrderEventType
) : IOrderEvent

class OrderCloseEvent(
    override val order: IOrder,
    override val type: OrderEventType
) : IOrderEvent

class OrderTPEvent(
    override val order: IOrder,
    override val type: OrderEventType
) : IOrderEvent

class OrderSLEvent(
    override val order: IOrder,
    override val type: OrderEventType
) : IOrderEvent

class OrderLabelEvent(
    override val order: IOrder,
    override val type: OrderEventType
) : IOrderEvent

class OrderCommentEvent(
    override val order: IOrder,
    override val type: OrderEventType
) : IOrderEvent

class OrderOpenPriceEvent(
    override val order: IOrder,
    override val type: OrderEventType
) : IOrderEvent

class OrderGTTEvent(
    override val order: IOrder,
    override val type: OrderEventType
) : IOrderEvent

class OrderAmountEvent(
    override val order: IOrder,
    override val type: OrderEventType
) : IOrderEvent


/*
sealed class OrderEvent(val order: IOrder, val type: OrderEventType) {
    class OrderTPEvent(val tpOrder: IOrder, val tpType: OrderEventType) : OrderEvent(tpOrder, tpType)
}

typealias OrderTPEvent = OrderEvent.OrderTPEvent*/
