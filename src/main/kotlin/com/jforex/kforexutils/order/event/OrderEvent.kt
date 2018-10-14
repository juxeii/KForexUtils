package com.jforex.kforexutils.order.event

import com.dukascopy.api.IMessage

data class OrderEvent(val message: IMessage, val messageType: OrderEventType)