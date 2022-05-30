package com.lukaslechner.coroutineusecasesonandroid.base

import timber.log.Timber

suspend fun <T> retry(
    retries: Int,
    initialDelayMillis: Long = 100L,
    maxDelayMillis: Long = 1000L,
    factor: Double = 2.0,
    onRepeatError: (t: Throwable) -> Any = { Timber.e(it) },
    block: suspend () -> T
): T {


    repeat(retries) {

    try {
            return block()
        } catch (e: Exception) {
            onRepeatError(e)
        }
    }

    return block()
}