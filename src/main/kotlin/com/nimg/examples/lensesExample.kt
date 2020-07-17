package com.nimg.examples

import org.http4k.core.*
import org.http4k.core.ContentType.Companion.TEXT_PLAIN
import org.http4k.filter.ServerFilters
import org.http4k.lens.Header
import org.http4k.lens.Query
import org.http4k.lens.int
import org.http4k.lens.string

fun main(args: Array<String>) {

    data class Child(val name: String)

    val nameHeader = Header.required("name")
    val ageQuery = Query.int().optional("age")
    val childrenBody =
        Body.string(TEXT_PLAIN).map({ it.split(",").map(::Child) }, { it.map { it.name }.joinToString() }).toLens()

    val endpoint = { request: Request ->

        val name: String = nameHeader(request)
        val age: Int? = ageQuery(request)
        val children: List<Child> = childrenBody(request)

        val msg = "$name is ${age ?: "unknown"} years old and has " +
                "${children.size} children (${children.map { it.name }.joinToString()})"
        Response(Status.OK).with(
            Body.string(TEXT_PLAIN).toLens() of msg
        )
    }

    val app = ServerFilters.CatchLensFailure.then(endpoint)

    val goodRequest = Request(Method.GET, "http://localhost:9000").with(
        nameHeader of "Jane Doe",
        ageQuery of 25,
        childrenBody of listOf(Child("Rita"), Child("Sue"))
    )

    println(listOf("", "Request:", goodRequest, app(goodRequest)).joinToString("\n"))

    val badRequest = Request(Method.GET, "http://localhost:9000")
        .with(nameHeader of "Jane Doe")
        .query("age", "some illegal age!")

    println(listOf("", "Request:", badRequest, app(badRequest)).joinToString("\n"))
}

