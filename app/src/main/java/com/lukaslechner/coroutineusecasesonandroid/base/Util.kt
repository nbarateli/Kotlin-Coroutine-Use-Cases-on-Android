package com.lukaslechner.coroutineusecasesonandroid.base

import kotlinx.coroutines.delay
import timber.log.Timber

suspend fun <T> retry(
    retries: Int,
    initialDelayMillis: Long = 100L,
    maxDelayMillis: Long = 2000L,
    factor: Double = 2.0,
    onRepeatError: (t: Throwable) -> Any = { Timber.e(it) },
    block: suspend () -> T
): T {


    var delayCurrent = initialDelayMillis
    repeat(retries) {
        try {
            return block()
        } catch (e: Exception) {
            onRepeatError(e)
            delay(delayCurrent)
            delayCurrent = (initialDelayMillis * factor).toLong().coerceAtMost(maxDelayMillis)
        }
    }

    return block()
}