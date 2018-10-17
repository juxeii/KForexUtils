package com.jforex.kforexutils.jfclient

import com.jforex.kforexutils.misc.KForexUtils

interface JFClientBridge
{
    fun onStart(utils: KForexUtils)

    fun onStop()
}