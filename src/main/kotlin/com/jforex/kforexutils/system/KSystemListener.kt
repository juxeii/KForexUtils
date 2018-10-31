package com.jforex.kforexutils.system

import com.dukascopy.api.system.ISystemListener
import com.jakewharton.rxrelay2.PublishRelay

class KSystemListener : ISystemListener {

    val connectionState: PublishRelay<ConnectionState> = PublishRelay.create()

    override fun onStart(processId: Long) {}

    override fun onStop(processId: Long) {}

    override fun onConnect() = connectionState.accept(ConnectionState.CONNECTED)

    override fun onDisconnect() = connectionState.accept(ConnectionState.DISCONNECTED)
}