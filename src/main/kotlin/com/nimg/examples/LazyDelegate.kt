package com.nimg.examples

val lazyValue: String by lazy {
    println("calculate")
    "Nimisha"
}

fun main() {
    println(lazyValue)
    println(lazyValue)
}