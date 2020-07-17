package com.nimg.examples

enum class CardType(val color: String) {
    SILVER("gray"),
    GOLD("yellow"),
    PLATINUM("black")
}

fun access() {
    val cardType: CardType = CardType.SILVER
    val color: String = CardType.PLATINUM.color
    println("card type: $cardType, color: $color")
}

fun main(args: Array<String>) {
    access()
}