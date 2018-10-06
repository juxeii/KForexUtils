package com.jforex.kforexutils.gateway

import com.dukascopy.api.IMessage

object MessageGateway
{
    fun onMessage(message: IMessage)
    {
        println("New message received $message")
    }
}
