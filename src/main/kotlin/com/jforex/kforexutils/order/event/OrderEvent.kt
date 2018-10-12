package com.jforex.kforexutils.order.event

import com.jforex.kforexutils.order.Order
import com.jforex.kforexutils.order.message.OrderMessage

data class OrderEvent(val message: OrderMessage, val messageType: OrderEventType) {
    val order: Order = message.order
}