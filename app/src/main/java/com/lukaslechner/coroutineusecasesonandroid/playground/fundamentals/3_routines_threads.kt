package com.lukaslechner.coroutineusecasesonandroid.playground.fundamentals

import kotlin.concurrent.thread

fun main() {
    println("main starts")
    threadRoutine(1, 500)
    threadRoutine(2, 300)
    println("main ends")

}

fun threadRoutine(number: Number, delay: Long) {
    thread {
        println("The routine $number started working")
        Thread.sleep(delay)
        println("The routine $number finished")
    }
}