package com.jforex.kforexutils.message

import com.dukascopy.api.IMessage

object MessageToOrderEvent
{
    fun convert(orderMessage: IMessage) = OrderEvent()
}