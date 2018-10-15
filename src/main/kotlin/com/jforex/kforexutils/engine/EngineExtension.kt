package com.jforex.kforexutils.engine

import com.dukascopy.api.IEngine
import com.jforex.kforexutils.engine.task.EngineOrderCreation
import com.jforex.kforexutils.misc.FieldProperty

internal var IEngine.orderCreation: EngineOrderCreation by FieldProperty<IEngine, EngineOrderCreation>()