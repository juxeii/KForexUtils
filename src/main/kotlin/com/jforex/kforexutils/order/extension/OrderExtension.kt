package com.jforex.kforexutils.order.extension

import com.dukascopy.api.IOrder

val IOrder.isCreated
    get() = state == IOrder.State.CREATED
val IOrder.isOpened
    get() = state == IOrder.State.OPENED
val IOrder.isFilled
    get() = state == IOrder.State.FILLED
val IOrder.isClosed
    get() = state == IOrder.State.CLOSED
val IOrder.isCanceled
    get() = state == IOrder.State.CANCELED
val IOrder.isConditional
    get() = orderCommand.isConditional
val IOrder.isPartiallyFilled
    get() = amount < requestedAmount
val IOrder.isFullyFilled
    get() = amount == requestedAmount