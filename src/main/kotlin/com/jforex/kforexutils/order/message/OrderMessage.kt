package com.jforex.kforexutils.order.message

import com.dukascopy.api.IMessage
import com.jforex.kforexutils.order.Order

data class OrderMessage(val order: Order, val message: IMessage)