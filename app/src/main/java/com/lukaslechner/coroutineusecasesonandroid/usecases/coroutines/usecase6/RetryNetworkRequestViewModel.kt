package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase6

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import kotlinx.coroutines.launch
import timber.log.Timber

class RetryNetworkRequestViewModel(
    private val api: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun performNetworkRequest() {
        uiState.postLoading()

        viewModelScope.launch {
            val retries = 2

            try {
                repeat(retries + 1) {
                    try {
                        return@launch requestData()
                    } catch (e: Exception) {
                        Timber.e(e)
                    }
                }
                requestData()
            } catch (e: Exception) {
                Timber.e(e)
                uiState.postError(e)
            }
        }
    }

    private suspend fun requestData() {
        api.getRecentAndroidVersions()
            .let(uiState::postSuccess)
    }

}