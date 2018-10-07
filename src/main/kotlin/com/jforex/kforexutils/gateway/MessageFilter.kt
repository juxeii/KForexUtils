package com.jforex.kforexutils.gateway

import com.dukascopy.api.IMessage

enum class MessageFilter
{
    ORDER
    {
        override fun isMatch(message: IMessage) = message.order != null
    },

    NOTIFICATION
    {
        override fun isMatch(message: IMessage) = message.type == IMessage.Type.NOTIFICATION
    },

    CONNECTION
    {
        override fun isMatch(message: IMessage) = message.type == IMessage.Type.CONNECTION_STATUS
    };

    abstract fun isMatch(message: IMessage): Boolean
}