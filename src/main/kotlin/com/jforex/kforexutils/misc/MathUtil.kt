package com.jforex.kforexutils.misc

import org.paukov.combinatorics3.Generator
import java.util.stream.Collectors

object MathUtil
{
    fun <T> kPowerSet(
        sourceSet: Collection<T>,
        setSize: Int
    ): Set<Set<T>> = Generator
        .combination(sourceSet)
        .simple(setSize)
        .stream()
        .map { HashSet(it) }
        .collect(Collectors.toSet())
}