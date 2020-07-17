package com.nimg.references

import com.natpryce.hamkrest.MatchResult
import com.natpryce.hamkrest.MatchResult.Match
import com.natpryce.hamkrest.MatchResult.Mismatch
import com.natpryce.hamkrest.Matcher


fun <T> containsAllElementsOf(expected: List<T>): Matcher<List<T>> =
    object : Matcher<List<T>> {
        override val description: String = "contains all elements of $expected"

        override fun invoke(actual: List<T>): MatchResult {
            val remainingExpected = actual.removeFrom(expected)

            return if (remainingExpected.isEmpty())
                Match
            else
                Mismatch("is $actual. $remainingExpected did not occur")
        }
    }

fun <T> List<T>.removeFrom(other: List<T>): List<T> =
    fold(
        mutableListOf<T>().apply { addAll(other) }
    ) { remainder, next ->
        remainder.apply { remove(next) }
    }.toList()