package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase4

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import timber.log.Timber

class VariableAmountOfNetworkRequestsViewModel(
    private val mockApi: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun performNetworkRequestsSequentially() {
        uiState.postLoading()
        viewModelScope.launch {
            try {
                uiState.postSuccess(
                    mockApi.getRecentAndroidVersions().map {
                        mockApi.getAndroidVersionFeatures(it.apiLevel)
                    }
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
                    mockApi.getRecentAndroidVersions().map {
                        async {
                            mockApi.getAndroidVersionFeatures(it.apiLevel)
                        }
                    }.awaitAll()
                )
            } catch (e: Exception) {
                Timber.e(e)
                uiState.postError(e)
            }
        }
    }
}