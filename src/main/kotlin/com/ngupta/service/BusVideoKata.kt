package com.ngupta.service

import kotlin.system.exitProcess


fun main() {
    print("Enter number of stops and maximum bus occupancy: ")
    val values = readLine()?.split(" ")?.map {
        it.toInt()
    } ?: emptyList()
    val validValues = values.filter { it > 0 }


    if (validValues.size != 2) {
        println("Invalid values passed for number of stops and(or) maximum bus occupancy")
        exitProcess(0)
    }

    val stops = values[0]
    val maxOccupancy = values[1]

    if (maxOccupancy <= 0) {
        println("Occupancy should be greater than 0")
        exitProcess(0)
    }

    val maxOccupancyRange = 0..maxOccupancy


    print("Enter sequence of passengers recorded by video system: ")
    val recorded = readLine()?.split(" ")?.map { it.toInt() }
        ?: emptyList()

    if (recorded.size != stops) {
        println("Passengers recorded should be equal to number of stops")
        exitProcess(0)
    }

    var possibility = 0

    for (startOccupancy in maxOccupancyRange) {
        var currentOccupancy = startOccupancy
        val isOptimalOccupancy = recorded.fold(listOf<Boolean>()) { acc, i ->
            currentOccupancy += i
            if (currentOccupancy in maxOccupancyRange) acc.plus(true) else acc.plus(false)
        }

        if (!isOptimalOccupancy.contains(false)) possibility += 1
    }

    println(possibility)
}

