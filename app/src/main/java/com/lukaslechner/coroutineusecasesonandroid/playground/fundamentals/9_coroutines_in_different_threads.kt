package com.lukaslechner.coroutineusecasesonandroid.playground.fundamentals

import kotlinx.coroutines.*

fun main() = runBlocking {
    println("main starts")
    joinAll(
        async { threadSwitchingCoroutine(1, 500) },
        async { threadSwitchingCoroutine(2, 300) }
    )
    println("main ends")

}

suspend fun threadSwitchingCoroutine(number: Number, delay: Long) {
    println("The routine $number started working on thread ${Thread.currentThread().name}")
    delay(delay)
    withContext(Dispatchers.Default) {
        println("The routine $number finished on thread ${Thread.currentThread().name}")
    }
}