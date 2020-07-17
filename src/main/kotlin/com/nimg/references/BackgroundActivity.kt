package com.nimg.references

interface BackgroundActivity : AutoCloseable {
    fun start()
    fun stop()

    override fun close() {
        stop()
    }
}

fun backgroundActivity(startFn: () -> Unit = {}, stopFn: () -> Unit) = object :
    BackgroundActivity {
    override fun start() = startFn()
    override fun stop() = stopFn()
}

class Modify : BackgroundActivity {
    override fun start() {
        println("in modify start")
    }

    override fun stop() {
        println("in modify stop")
    }
}

fun applyFunctionality() {
    Modify().apply { this.start() }.stop()
}

fun main(args: Array<String>) {
    applyFunctionality()
}
