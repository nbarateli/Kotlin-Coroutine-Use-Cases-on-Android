package com.lukaslechner.coroutineusecasesonandroid.playground.fundamentals

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    println("main starts")
    joinAll(
        async { coroutineWithInfo(1, 500) },
        async { coroutineWithInfo(2, 300) }
    )
    println("main ends")

}

suspend fun coroutineWithInfo(number: Number, delay: Long) {
    println("The routine $number started working on thread ${Thread.currentThread().name}")
    delay(delay)
    println("The routine $number finished on thread ${Thread.currentThread().name}")
}