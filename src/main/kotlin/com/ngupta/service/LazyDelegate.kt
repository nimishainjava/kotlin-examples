package com.ngupta.service

val lazyValue : String by lazy {
     println("calculate")
    "Nimisha"
}

fun main() {
    println(lazyValue)
    println(lazyValue)
}