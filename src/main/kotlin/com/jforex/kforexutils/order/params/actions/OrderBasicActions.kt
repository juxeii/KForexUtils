package com.jforex.kforexutils.order.params.actions

import com.jforex.kforexutils.misc.ErrorConsumer
import com.jforex.kforexutils.misc.KRunnable
import com.jforex.kforexutils.misc.emptyAction
import com.jforex.kforexutils.misc.emptyErrorConsumer

data class OrderBasicActions @JvmOverloads constructor(
    val onStart: KRunnable = emptyAction,
    val onComplete: KRunnable = emptyAction,
    val onError: ErrorConsumer = emptyErrorConsumer
)