package com.lukaslechner.coroutineusecasesonandroid.playground.fundamentals

import kotlinx.coroutines.runBlocking
import java.lang.Thread.sleep
import kotlin.concurrent.thread

fun main(): Unit = runBlocking {
    repeat(Int.MAX_VALUE) {
        thread {
            sleep(5000)
            print(".")
        }
    }
}