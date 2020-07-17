package com.nimg.examples

val multiplier: (Int, Int) -> Int = { a, b -> a * b }

fun main(args: Array<String>) {
    val result: Int = multiplier(4, 6)
    println(result)
    println(multiplier(2, 3))
}