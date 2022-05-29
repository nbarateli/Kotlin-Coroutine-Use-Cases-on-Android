package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase2

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import kotlinx.coroutines.launch
import timber.log.Timber

class Perform2SequentialNetworkRequestsViewModel(
    private val mockApi: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun perform2SequentialNetworkRequest() {
        uiState.postLoading()

        viewModelScope.launch {
            try {
                mockApi.getRecentAndroidVersions()
                    .lastOrNull()?.apiLevel
                    ?.let { mockApi.getAndroidVersionFeatures(it) }
                    ?.let(uiState::postSuccess)
            } catch (e: Exception) {
                Timber.e(e)
                uiState.postError(e)
            }
        }
    }

}