package com.jforex.kforexutils.client

import com.dukascopy.api.system.IClient
import com.jforex.kforexutils.authentification.PinProvider
import com.jforex.kforexutils.settings.PlatformSettings
import com.jforex.kforexutils.system.KSystemListener
import org.aeonbits.owner.ConfigFactory

fun IClient.init()
{
    val systemListener = KSystemListener()
    val platformSettings: PlatformSettings = ConfigFactory.create(PlatformSettings::class.java)
    this.systemListener = systemListener
    this.platformSettings = platformSettings
    this.pinProvider = PinProvider(this, platformSettings.liveConnectURL())
    setSystemListener(systemListener)
}