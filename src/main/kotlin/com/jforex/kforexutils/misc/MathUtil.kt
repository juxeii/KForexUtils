package com.jforex.kforexutils.misc

import com.google.common.collect.Sets.newHashSet
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
        .map { newHashSet<T>(it) }
        .collect(Collectors.toSet())
}