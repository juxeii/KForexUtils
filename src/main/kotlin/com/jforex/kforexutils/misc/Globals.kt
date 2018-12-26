package com.jforex.kforexutils.misc

import com.jforex.kforexutils.order.task.OrderCallHandlers
import java.math.BigDecimal

@DslMarker
annotation class OrderDsl

internal val emptyAction: KRunnable = { }
internal val emptyOrderConsumer: OrderConsumer = {}
internal val emptyErrorConsumer: ErrorConsumer = { }
internal val emptyOrderEventConsumer: OrderEventConsumer = {}
internal val emptyCallHandlers = OrderCallHandlers()

fun Double.toAmount() =
    BigDecimal(this)
        .setScale(2, BigDecimal.ROUND_HALF_UP)
        .toDouble()