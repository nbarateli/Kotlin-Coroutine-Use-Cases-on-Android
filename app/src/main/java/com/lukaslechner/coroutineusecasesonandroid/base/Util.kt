package com.lukaslechner.coroutineusecasesonandroid.base

import kotlinx.coroutines.delay
import timber.log.Timber
import kotlin.math.min

suspend fun <T> retry(
    retries: Int,
    initialDelayMillis: Long = 100L,
    maxDelayMillis: Long = 1000L,
    onRepeatError: (t: Throwable) -> Any = { Timber.e(it) },
    block: suspend () -> T
): T {


    var pow = 0
    repeat(retries) {
        try {
            return block()
        } catch (e: Exception) {
            onRepeatError(e)
            delay(min(initialDelayMillis shl ++pow, maxDelayMillis))
        }
    }

    return block()
}