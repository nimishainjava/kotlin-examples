package com.ngupta.service

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.ZonedDateTime
import java.time.ZoneId



data class Customer(val name: String, val age:Int)
data class Address(val street:String, val postalCode: String)

val customerMap = mapOf(Pair(Customer("Jack", 25), Address("NANTERRE CT", "77471")),
    Pair(Customer("Mary", 37), Address("W NORMA ST", "77009")),
    Pair(Customer("Peter", 18), Address("S NUGENT AVE", "77571")),
    Pair(Customer("Amos", 23), Address("E NAVAHO TRL", "77449")),
    Pair(Customer("Craig", 45), Address("AVE N", "77587")))

val customerList = customerMap.flatMap { (customer, address) -> listOf(customer) }

/*
	Customer(name=Jack, age=25)
	Customer(name=Mary, age=37)
	Customer(name=Peter, age=18)
	Customer(name=Amos, age=23)
	Customer(name=Craig, age=45)
*/

val addressList = customerMap.
    flatMap { (customer, address) ->
        listOf(address) }
/*
	Address(street=NANTERRE CT, postcode=77471)
	Address(street=W NORMA ST, postcode=77009)
	Address(street=S NUGENT AVE, postcode=77571)
	Address(street=E NAVAHO TRL, postcode=77449)
	Address(street=AVE N, postcode=77587)
*/

//val customerInfos = customerMap.flatMap {}

/*
	Jack lives at NANTERRE CT
	Mary lives at W NORMA ST
	Peter lives at S NUGENT AVE
	Amos lives at E NAVAHO TRL
	Craig lives at AVE N
*/

fun main() {
    //customerList.forEach{ println(it) }
    addressList.forEach{ println(it) }
    //customerInfos.forEach{ println(it) }

    val now = ZonedDateTime.of(LocalDateTime.now(), ZoneId.of("UTC"))

    //println(ZoneId.getAvailableZoneIds())
    //println(now.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
}