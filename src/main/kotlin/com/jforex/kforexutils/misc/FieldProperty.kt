package com.jforex.kforexutils.misc

import java.util.*
import kotlin.reflect.KProperty

class FieldProperty<R, T : Any>(val initializer: R.() -> T = { throw IllegalStateException("Not initialized.") })
{
    private val map = WeakHashMap<R, T>()

    operator fun getValue(thisRef: R, property: KProperty<*>): T =
        map[thisRef] ?: setValue(thisRef, property, initializer(thisRef))

    operator fun setValue(thisRef: R, property: KProperty<*>, value: T): T
    {
        map[thisRef] = value
        return value
    }
}