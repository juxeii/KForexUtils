package com.jforex.kforexutils.misc

import com.dukascopy.api.IMessage
import com.jforex.kforexutils.order.event.OrderEvent

typealias Consumer<T> = (T) -> Unit
typealias MessageConsumer = Consumer<IMessage>
typealias OrderEventConsumer = Consumer<OrderEvent>
typealias ErrorConsumer = Consumer<Throwable>

typealias KRunnable = () -> Unit
typealias KCallable<T> = () -> T