package com.jforex.kforexutils.engine

import com.dukascopy.api.IEngine
import com.jforex.kforexutils.misc.FieldProperty
import com.jforex.kforexutils.misc.KForexUtils

internal var IEngine.kForexUtils: KForexUtils by FieldProperty()
