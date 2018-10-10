package com.jforex.kforexutils.order.event

import com.dukascopy.api.IMessage
import com.dukascopy.api.IOrder

data class OrderEvent(val message: IMessage, val messageType: OrderEventType)
{
    val order: IOrder = message.order
}