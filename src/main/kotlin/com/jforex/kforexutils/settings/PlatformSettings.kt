package com.jforex.kforexutils.settings

import org.aeonbits.owner.Config

@Config.Sources("file:PlatformSettings.properties")
interface PlatformSettings : Config {

    @Config.Key("strategy.threadname")
    @Config.DefaultValue("Strategy")
    fun strategyThreadPrefix(): String

    @Config.Key("connection.demourl")
    @Config.DefaultValue("http://platform.dukascopy.com/demo_3/jforex_3.jnlp")
    fun demoConnectURL(): String

    @Config.Key("connection.liveurl")
    @Config.DefaultValue("http://platform.dukascopy.com/live_3/jforex_3.jnlp")
    fun liveConnectURL(): String

    @Config.Key("history.access.retries")
    @Config.DefaultValue("10")
    fun historyAccessRetries(): Long

    @Config.Key("history.access.retrydelay")
    @Config.DefaultValue("1000")
    fun historyAccessRetryDelay(): Long
}