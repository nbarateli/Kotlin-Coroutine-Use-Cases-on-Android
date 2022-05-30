package com.lukaslechner.coroutineusecasesonandroid.base

import timber.log.Timber

suspend fun <T> retry(
    retries: Int,
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