package com.jforex.kforexutils.order.event

import com.jforex.kforexutils.misc.ErrorConsumer
import com.jforex.kforexutils.misc.EventHandlers
import com.jforex.kforexutils.misc.KRunnable

data class OrderEventSubscribeParams(
    val eventHandlers: EventHandlers,
    val onComplete: KRunnable,
    val onError: ErrorConsumer
)