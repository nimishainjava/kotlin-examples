package com.nimg.examples

import org.http4k.core.*
import org.http4k.core.ContentType.Companion.TEXT_PLAIN
import org.http4k.core.Method.*
import org.http4k.core.Status.Companion.OK
import org.http4k.format.Jackson
import org.http4k.format.Jackson.json
import org.http4k.lens.Query
import org.http4k.lens.int
import org.http4k.lens.string
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Jetty
import org.http4k.server.asServer

fun add(value1: Int, value2: Int): HttpHandler = {
    Response(OK).with(
        Body.string(TEXT_PLAIN).toLens() of (value1 + value2).toString()
    )
}

val ageQuery = Query.int().required("age")

val objectUsingExtensionFunctions =
    Jackson.obj(
        "thisIsAString" to Jackson.string("stringValue"),
        "thisIsANumber" to Jackson.number(12345),
        "thisIsAList" to Jackson.array(listOf(Jackson.boolean(true)))
    )


fun echo(name: String): HttpHandler = {
    Response(OK).with(
        Body.json().toLens() of objectUsingExtensionFunctions
    )
}

val app = routes(
    "bob" bind GET to { Response(OK).body("you GET bob") },
    "rita" bind POST to { Response(OK).body("you POST rita") },
    "sue" bind DELETE to { Response(OK).body("you DELETE sue") }
)

fun main(args: Array<String>) {
    println(app(Request(GET, "/bob")))
    println(app(Request(POST, "/bob")))
    println(app(Request(DELETE, "/sue")))

    val request: Request = Request(Method.GET, "/").query("age", "20")

    echo("Nimisha").asServer(Jetty(9000)).start()

    println("Approach 2:  -" + echo("Nimisha")(request).bodyString())
}
