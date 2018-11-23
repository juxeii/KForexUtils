package com.jforex.kforexutils.client

import com.jforex.kforexutils.settings.PlatformSettings
import com.jforex.kforexutils.system.KSystemListener

internal data class IClientContext(
    val systemListener: KSystemListener,
    val platformSettings: PlatformSettings
)