package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase5

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.withTimeoutOrNull
import timber.log.Timber

class NetworkRequestWithTimeoutViewModel(
    private val api: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun performNetworkRequest(timeout: Long) {
        uiState.postLoading()

        usingWIthTimeoutOrNull(timeout)
    }

    private fun usingWIthTimeout(timeout: Long) {
        viewModelScope.launch {
            try {
                withTimeout(timeout) {
                    api.getRecentAndroidVersions()
                        .let(uiState::postSuccess)
                }
            } catch (ce: TimeoutCancellationException) {
                Timber.e(ce)
                uiState.postError("Timed out")
            } catch (e: Exception) {
                Timber.e(e)
                uiState.postError(e)
            }
        }
    }

    private fun usingWIthTimeoutOrNull(timeout: Long) {
        viewModelScope.launch {

            try {
                withTimeoutOrNull(timeout) {
                    api.getRecentAndroidVersions()
                }?.let(uiState::postSuccess)
                    ?: uiState.postError("Timed Out")
            } catch (e: Exception) {
                Timber.e(e)
                uiState.postError(e)
            }
        }
    }
}