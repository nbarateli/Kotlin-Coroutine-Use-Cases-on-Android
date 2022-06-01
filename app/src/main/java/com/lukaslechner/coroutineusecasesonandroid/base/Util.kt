package com.lukaslechner.coroutineusecasesonandroid.base

import kotlinx.coroutines.delay
import kotlinx.coroutines.withTimeout
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

suspend fun <T> retryWithTimeout(
    retries: Int,
    timeout: Long,
    initialDelayMillis: Long = 100L,
    maxDelayMillis: Long = 100L,
    factor: Double = 1.0,
    onRepeatError: (t: Throwable) -> Any = { Timber.e(it) },
    block: suspend () -> T
): T =
    retry(retries, initialDelayMillis, maxDelayMillis, factor, onRepeatError) {
        withTimeout(timeout) {
            block()
        }
    }

