package com.lukaslechner.coroutineusecasesonandroid.playground.coroutinebuilders

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


fun main() = runBlocking<Unit> {
    launch(start = CoroutineStart.LAZY) {
        println(networkRequest())
    }.also { delay(200) }.join()
    println("We're in the endgame now")
}

suspend fun networkRequest(): String = delay(100).let { "Result" }