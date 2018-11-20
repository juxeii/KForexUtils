package com.jforex.kforexutils.misc

import com.dukascopy.api.IMessage
import com.dukascopy.api.IOrder
import com.jforex.kforexutils.order.event.IOrderEvent
import com.jforex.kforexutils.order.event.OrderEventType

typealias Consumer<T> = (T) -> Unit
typealias OrderConsumer = Consumer<IOrder>
typealias MessageConsumer = Consumer<IMessage>
typealias OrderEventConsumer = Consumer<IOrderEvent>
typealias ErrorConsumer = Consumer<Throwable>
typealias EventHandlers = Map<OrderEventType, OrderEventConsumer>
typealias EventTypes = Set<OrderEventType>

typealias KRunnable = () -> Unit
typealias KCallable<T> = () -> T