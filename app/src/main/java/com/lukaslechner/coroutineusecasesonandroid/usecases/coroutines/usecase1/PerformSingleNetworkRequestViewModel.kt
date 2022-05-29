package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase1

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import kotlinx.coroutines.launch
import timber.log.Timber

class PerformSingleNetworkRequestViewModel(
    private val mockApi: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun performSingleNetworkRequest() {
        uiState.postLoading()

        viewModelScope.launch {
            try {
                mockApi.getRecentAndroidVersions()
                    .let(uiState::postSuccess)
            } catch (e: Exception) {
                Timber.e(e)
                uiState.postError(e)
            }
        }
    }
}