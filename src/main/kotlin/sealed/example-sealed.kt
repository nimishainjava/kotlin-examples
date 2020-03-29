package sealed

sealed class Animal {
    var type : String =""
    var age : Int = 0

    open fun eat() {
    println("default animal eat method")
    }
}

class Dog : Animal() {

    override fun eat() {
        type = "dog"
        age = 3
        println("Animal type: $type whose age: $age")

    }
}

fun main(args: Array<String>) {
    Dog().eat()

    //sealed classes are abstract
    // cannot be instantiated, have private constructor
    //Animal().eat()
    //App("Nimisha").start()


}

class App(val internalServer: String, val port: Int = 0) {

    fun start() {
        println("starting server $internalServer")
    }

}