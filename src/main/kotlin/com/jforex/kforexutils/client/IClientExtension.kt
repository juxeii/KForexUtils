package com.jforex.kforexutils.client

import com.dukascopy.api.system.IClient
import com.jforex.kforexutils.authentification.PinProvider
import com.jforex.kforexutils.settings.PlatformSettings
import com.jforex.kforexutils.system.ConnectionState
import com.jforex.kforexutils.system.KSystemListener
import io.reactivex.Observable
import org.aeonbits.owner.ConfigFactory

internal lateinit var platformSettings: PlatformSettings
internal lateinit var pinProvider: PinProvider
internal lateinit var connectionState: Observable<ConnectionState>

fun IClient.init()
{
    val systemListener = KSystemListener()
    connectionState = systemListener.connectionState
    platformSettings = ConfigFactory.create(PlatformSettings::class.java)
    pinProvider = PinProvider(this, platformSettings.liveConnectURL())
    setSystemListener(systemListener)
}