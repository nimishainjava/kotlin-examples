package com.nimg.examples

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.ObjectMapper


fun main(args: Array<String>) {
    val jsonInput: String = "{\n" +
            " \"matchCriteria\": [\n" +
            "   {\n" +
            "\t\"type\":\"EmailDomain\",\n" +
            "    \"value\": \"aber.ac.u\"\n" +
            "   }\n" +
            "]\n" +
            "}"

    val objectMapper: ObjectMapper = ObjectMapper()

    //objectMapper.valueToTree<Search>()

    val sequence = sequenceOf("a", "b", "c", "d", "e", "f", "g", "h")
    sequence.map {
        println("Applying map function for $it")
        it
    }

    println(calc(1, 2, { a, b -> a + b }))
}

fun calc(a: Int, b: Int, opr: (Int, Int) -> Int) = opr(a, b)

data class Search(val matchCriteria: List<MatchingCriterion>)
data class MatchingCriterion(val type: CriterionType, val value: String)

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
enum class CriterionType {
    GridId,
    Ip,
    EmailDomain
}

