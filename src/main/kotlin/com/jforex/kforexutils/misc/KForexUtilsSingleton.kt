package com.jforex.kforexutils.misc

import com.dukascopy.api.IContext
import com.jforex.kforexutils.order.OrderTaskExecutor
import com.jforex.kforexutils.thread.StrategyThread

class KForexUtilsSingleton private constructor(private val context: IContext)
{
    val engine = context.engine
    val strategyThread = StrategyThread(context)
    val orderTaskExecutor = OrderTaskExecutor(engine, strategyThread)

    companion object : SingletonHolder<KForexUtilsSingleton, IContext>(::KForexUtilsSingleton)
}