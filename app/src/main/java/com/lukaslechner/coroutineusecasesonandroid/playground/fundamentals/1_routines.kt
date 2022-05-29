package com.lukaslechner.coroutineusecasesonandroid.playground.fundamentals

fun main() {
    println("main starts")
    routine(1, 500)
    routine(2, 300)
    println("main ends")

}

fun routine(number: Number, delay: Long) {
    println("The routine $number started working")
    Thread.sleep(delay)
    println("The routine $number finished")
}