package com.jforex.kforexutils.misc

import com.dukascopy.api.IContext
import com.jforex.kforexutils.thread.StrategyThread

class ComponentFactory(val context: IContext) {
    val strategyThread = StrategyThread(context)
    val jfEngine = context.engine
}