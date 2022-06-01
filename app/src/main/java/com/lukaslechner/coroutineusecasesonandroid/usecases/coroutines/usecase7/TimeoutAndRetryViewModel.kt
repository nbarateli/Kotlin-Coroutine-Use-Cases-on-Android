package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase7

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.base.retryWithTimeout
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import timber.log.Timber

class TimeoutAndRetryViewModel(
    private val api: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun performNetworkRequest() {
        uiState.postLoading()
        val requests = listOf(
            viewModelScope.async {
                retryWithTimeout(2, 1000) {
                    api.getAndroidVersionFeatures(27)
                }
            },
            viewModelScope.async {
                retryWithTimeout(2, 1000) {
                    api.getAndroidVersionFeatures(28)
                }
            },
        )

        viewModelScope.launch {
            try {
                uiState.postSuccess(requests.awaitAll())
            } catch (e: Exception) {
                Timber.e(e)
                uiState.postError(e)
            }
        }
    }
}