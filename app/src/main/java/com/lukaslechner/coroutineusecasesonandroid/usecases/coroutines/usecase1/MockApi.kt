package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase1

import com.lukaslechner.coroutineusecasesonandroid.mock.createMockApi
import com.lukaslechner.coroutineusecasesonandroid.utils.MockNetworkInterceptor

fun mockApi() =
    createMockApi(
        MockNetworkInterceptor()
            .mock(
                "http://localhost/recent-android-versions",
                "Something is wrong",
                400,
                700
            )
    )