package com.jforex.kforexutils.message

import com.dukascopy.api.IMessage

enum class MessageFilter
{
    CALENDAR
    {
        override fun isMatch(message: IMessage) = message.type == IMessage.Type.CALENDAR
    },

    CONNECTION
    {
        override fun isMatch(message: IMessage) = message.type == IMessage.Type.CONNECTION_STATUS
    },

    INSTRUMENT
    {
        override fun isMatch(message: IMessage) = message.type == IMessage.Type.INSTRUMENT_STATUS
    },

    EMAIL
    {
        override fun isMatch(message: IMessage) = message.type == IMessage.Type.MAIL
    },

    NEWS
    {
        override fun isMatch(message: IMessage) = message.type == IMessage.Type.NEWS
    },

    NOTIFICATION
    {
        override fun isMatch(message: IMessage) = message.type == IMessage.Type.NOTIFICATION
    },

    ORDER
    {
        override fun isMatch(message: IMessage) = message.order != null
    },

    STRATEGY
    {
        override fun isMatch(message: IMessage) = message.type == IMessage.Type.STRATEGY_BROADCAST
    },

    WITHDRAWAL
    {
        override fun isMatch(message: IMessage) = message.type == IMessage.Type.WITHDRAWAL
    };

    abstract fun isMatch(message: IMessage): Boolean
}