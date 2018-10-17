package com.jforex.kforexutils.settings

import org.aeonbits.owner.Config

@Config.Sources("file:PlatformSettings.properties")
interface PlatformSettings : Config {

    @Config.Key("strategy.threadname")
    @Config.DefaultValue("Strategy")
    fun strategyThreadPrefix(): String
}