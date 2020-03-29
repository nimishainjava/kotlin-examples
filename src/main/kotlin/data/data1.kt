package data

data class User(var firstName: String,
                var lastName: String,
                var birthYear: String)

val user = User("Bruce", "Wayne", "1965")
val userCopy = user.copy("John")

fun main(args: Array<String>) {
    println("Original user: $user")
    println("Copied user: $userCopy")
}