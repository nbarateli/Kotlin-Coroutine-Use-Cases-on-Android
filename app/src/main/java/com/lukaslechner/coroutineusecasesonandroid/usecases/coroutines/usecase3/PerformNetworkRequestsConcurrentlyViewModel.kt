package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase3

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import timber.log.Timber

class PerformNetworkRequestsConcurrentlyViewModel(
    private val mockApi: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun performNetworkRequestsSequentially() {
        uiState.postLoading()
        viewModelScope.launch {
            try {
                uiState.postSuccess(
                    listOf(
                        mockApi.getAndroidVersionFeatures(27),
                        mockApi.getAndroidVersionFeatures(28),
                        mockApi.getAndroidVersionFeatures(29),
                    )
                )
            } catch (e: Exception) {
                Timber.e(e)
                uiState.postError(e)
            }
        }
    }

    fun performNetworkRequestsConcurrently() {
        uiState.postLoading()

        viewModelScope.launch {
            try {

                uiState.postSuccess(
                    awaitAll(
                        viewModelScope.async { mockApi.getAndroidVersionFeatures(27) },
                        viewModelScope.async { mockApi.getAndroidVersionFeatures(28) },
                        viewModelScope.async { mockApi.getAndroidVersionFeatures(29) },
                    )
                )
            } catch (e: Exception) {
                Timber.e(e)
                uiState.postError(e)
            }
        }

    }
}