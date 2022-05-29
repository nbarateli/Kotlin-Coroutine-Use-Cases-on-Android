package com.lukaslechner.coroutineusecasesonandroid.playground.coroutinebuilders

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main() = runBlocking<Unit> {
    val startTime = System.currentTimeMillis()
    val def1 = async(start = CoroutineStart.LAZY) {
        println("started result1 on ${startTime.elapsedMillis()}")
        networkCall(1).apply {
            println("received 1 after ${startTime.elapsedMillis()}")
        }
    }

    val def2 = async {
        println("started result2 on ${startTime.elapsedMillis()}")
        networkCall(2).apply {
            println("received 2 after ${startTime.elapsedMillis()}")
        }
    }
    println("${def1.await()} ${def2.await()}")

}


suspend fun networkCall(number: Number) = delay(500).let { "Result $number" }

fun Long.elapsedMillis() = System.currentTimeMillis() - this