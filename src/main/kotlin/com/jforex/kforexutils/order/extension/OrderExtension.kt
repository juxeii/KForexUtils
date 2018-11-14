package com.jforex.kforexutils.order.extension

import com.dukascopy.api.IOrder
import com.jforex.kforexutils.misc.FieldProperty
import com.jforex.kforexutils.misc.KForexUtils

internal var IOrder.kForexUtils: KForexUtils by FieldProperty()

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