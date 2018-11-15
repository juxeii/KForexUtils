package com.jforex.kforexutils.context

import com.dukascopy.api.IContext
import com.jforex.kforexutils.misc.FieldProperty
import com.jforex.kforexutils.settings.PlatformSettings

internal var IContext.platformSettings: PlatformSettings by FieldProperty()